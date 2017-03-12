(ns module-command-parser.parser
  (:require [clojure.string :refer [split, starts-with?, join]])
  (:gen-class))

(defn parse-cmd-name
  "Parses module command name from final arg of rx-irc-cmd"
  [final-arg]
  (join "-" (filter (fn [arg] (not (starts-with? arg "-"))) (split (subs final-arg 1) #"\s+"))))

(defn parse-cmd-args
  "Parses module command args from final arg rx-irc-cmd"
  [final-arg]
  (vec (filter (fn [arg] (starts-with? arg "-")) (split (subs final-arg 1) #"\s+"))))

(defn parse-module-cmd
  "Parses module command name and module command args from final arg of rx-irc-cmd"
  [final-arg]
  {:cmd-name (parse-cmd-name final-arg) :cmd-args (parse-cmd-args final-arg)})
  

