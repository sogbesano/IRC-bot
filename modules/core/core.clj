(ns modules.core.core
  (:require [irc-command.command :refer [new-privmsg-cmd]]
            [clojure.string :refer [join]])
  (:gen-class))

(defn caw
  "CAW command yields a PRIVMSG where the final arg is a random length crow onomatopoeia in japanese kana unicode"
  []
  (let [rng (rand-int 50) jp-kana-crow-onomatopoeia (join " " (repeat (max 2 rng) "\u304B\u30FC!"))] 
    (new-privmsg-cmd "#sett" jp-kana-crow-onomatopoeia)))

(defn botsnack
  "BOTSNACK command yields a PRIVMSG where the final arg is itadakimasu in japanese hiragana unicode"
  []
  (let [jp-hiragana-itadakimasu "\u3044\u305F\u3060\u304D\u307E\u3059!"]
  (new-privmsg-cmd "#sett" jp-hiragana-itadakimasu)))

(defn golem
  "rjp golem - if rx-irc-msg contains? girls are/r dumb then change NICK to the_real_golem and send 'actually, i think intellect is more a product of socialisation and age rather than gender' then change NICK back to narkoman"
  []
  )

(defn fart
  "probabilistic jp fart onomatopoeia"
  []
  )

(def module-name-and-cmds {:module-name "core" :caw caw :botsnack botsnack})
