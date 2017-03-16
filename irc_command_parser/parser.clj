(ns irc-command-parser.parser
  (:require [clojure.string :refer [index-of, includes?, trim, join, split]])
  (:require [irc-command.command :refer [new-irc-cmd]])
  (:gen-class)
)

(defn parse-source
  "Parses IRC source data from rx-irc-cmd"
  [rx-irc-cmd]
  (subs rx-irc-cmd (index-of rx-irc-cmd ":") (index-of rx-irc-cmd " "))
)

(defn parse-nick
  "Parses IRC nick data from source data of rx-irc-cmd"
  [src]
  (if (includes? src "!") 
    (subs src (+ (index-of src ":") 1) (index-of src "!")) 
    nil
  )
)

(defn parse-username
  "Parses IRC username data from source data of rx-irc-cmd"
  [src]
  (if (includes? src "~") 
    (subs src (+ (index-of src "~") 1) (index-of src "@")) 
    nil
  )
)

(defn parse-host
  "Parses IRC host data from rx-irc-cmd"
  [rx-irc-cmd]
  (let 
    [
      host-and-tail (if (re-find #"@(\w+)" rx-irc-cmd) (subs rx-irc-cmd (+ (index-of rx-irc-cmd "@") 1)) " ")
    ]
    (if (includes? host-and-tail " ") 
      (subs host-and-tail 0 (index-of host-and-tail " ")) 
      host-and-tail
    )
  )
)

(defn parse-cmd-name
  "Parses IRC command name data from rx-irc-cmd"
  [rx-irc-cmd]
  (let 
    [
      matcher (re-matcher #"(\s+)\w+|\d+(\s+)" rx-irc-cmd)
    ]
    (if (re-find matcher) 
      (trim (join "" (re-groups matcher))) 
      nil
    )
  )
)

(defn parse-cmd-args
  "Parses IRC command args data from rx-irc-cmd"
  [rx-irc-cmd]
  (let 
    [
      cmd-matcher (re-matcher #"(\s+)\w+|\d+(\s+)" rx-irc-cmd) cmd-args-matcher (re-matcher #" :(.+)" rx-irc-cmd)
    ]
    (.find cmd-matcher);;move matcher to cmd
    (if (.find cmd-args-matcher)
      (split (trim (subs rx-irc-cmd (.end cmd-matcher) (.start cmd-args-matcher))) #"\s+")
      nil
    )
  )
)

(defn parse-final-arg
  "Parses IRC final-arg data from rx-irc-cmd"
  [rx-irc-cmd]
  (let 
    [
      final-arg-matcher (re-matcher #"(\s+):(.+)" rx-irc-cmd)
    ]
    (if (.find final-arg-matcher) 
      (trim (subs rx-irc-cmd (+ (.start final-arg-matcher) 2) (count rx-irc-cmd)))
      nil
    )
  )
) 

(defn parse-irc-cmd
  "Parses all IRC command data from rx-irc-cmd"
  [rx-irc-cmd]
  (let 
    [
      src (parse-source rx-irc-cmd)
      nick (parse-nick src)
      username (parse-username src)
      host (parse-host rx-irc-cmd)
      cmd-name (parse-cmd-name rx-irc-cmd)
      cmd-args (parse-cmd-args rx-irc-cmd)
      final-arg (parse-final-arg rx-irc-cmd)
    ]
    (new-irc-cmd src nick username host cmd-name cmd-args final-arg)
  )
)
