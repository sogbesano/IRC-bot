(ns config-parser.parser
  (:require [clojure.java.io :refer [file]])
  (:gen-class)
)

(def config-file (var-get (load-file (format "%s/src/%s" (.getCanonicalPath (file ".")) "config.clj")))) 

(defn parse-host
  "Parses the host from the network configurations of config.clj"
  [config]
  (:host (:network config))
)

(defn parse-port
  "Parses the port from the network configurations of config.clj"
  [config]
  (:port (:network config))
)

(defn parse-nick
  "Parses the nick from the irc configurations of config.clj"
  [config]
  (:nick (:irc config))
)

(defn parse-username
  "Parses the username from the irc configurations of config.clj"
  [config]
  (:username (:irc config))
)

(defn parse-channel
  "Parses the channel from the irc configurations of config.clj"
  [config]
  (:channel (:irc config))
)

(defn parse-module-prompt
  [config]
  (:module-prompt config)
)
