(ns modules.utils
  (:require [clojure.string :refer [starts-with?]])
  (:gen-class))

(defn starts-with-module-prompt?
  "Returns true if final-arg of parsed-rx-irc-cmd starts with module prompt"
  [final-arg, module-prompt]
  (starts-with? (if (nil? final-arg) "" final-arg) module-prompt))
