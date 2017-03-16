(ns modules.core.core
  (:require [irc-command.command :refer [new-privmsg-cmd]]
            [clojure.string :refer [join, replace]])
  (:require [irc-bot.core :refer [loaded-modules]])
  (:require [module-command.command :refer [get-loaded-modules-keys]])
  (:gen-class))

(defn caw
  "CAW cmd yields a PRIVMSG where the final arg is a random length crow onomatopoeia in japanese kana unicode"
  []
  (let [rng (rand-int 50) jp-kana-crow-onomatopoeia (join " " (repeat (max 2 rng) "\u304B\u30FC!"))] 
    (new-privmsg-cmd "#sett" jp-kana-crow-onomatopoeia)))

(defn botsnack
  "BOTSNACK cmd yields a PRIVMSG where the final arg is itadakimasu in japanese hiragana unicode"
  []
  (let [jp-hiragana-itadakimasu "\u3044\u305F\u3060\u304D\u307E\u3059!"]
  (new-privmsg-cmd "#sett" jp-hiragana-itadakimasu)))

(defn ls-cmds
  "LS-CMDS cmd yields a PRIVMSG where the final arg is formatted output of all module commands for all loaded modules"
  []
  (let 
    [
     all-loaded-module-cmds (get-loaded-modules-keys loaded-modules)
     all-module-cmds-comma-seperated (replace (join ", " all-loaded-module-cmds) #":" "")
     all-module-cmds-fmtd (format "[%s]" all-module-cmds-comma-seperated)
    ]
    (new-privmsg-cmd "#sett" all-module-cmds-fmtd)))


  
(def module-name-and-cmds {:module-name "core" :caw caw :botsnack botsnack :ls-cmds ls-cmds})
