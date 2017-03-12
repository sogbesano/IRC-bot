(ns irc-command.command
  (:require [clojure.string :refer [join, blank?]])
  (:gen-class))

(defn new-irc-cmd
  "Creates a new IRC command"
  [source, nick, username, host, cmd-name, cmd-args, final-arg]
  {:source source :nick nick :username username :host host :cmd-name cmd-name :cmd-args cmd-args :final-arg final-arg})

(defn fmt-for-tx
  "Formats the IRC command for transmission to the IRC server"
  [irc-cmd]
  (let [{irc-cmd-cmd-args :cmd-args irc-cmd-final-arg :final-arg cmd-name :cmd-name} irc-cmd
         cmd-args (if (coll? irc-cmd-cmd-args) (join " " irc-cmd-cmd-args) irc-cmd-cmd-args)
         final-arg (if (blank? (or irc-cmd-final-arg "")) "" (str ":" irc-cmd-final-arg))
         cr-nl "\r\n"]
         (format "%s %s %s%s" cmd-name cmd-args final-arg cr-nl))) 

(defn new-nick-cmd
  "Creates IRC command NICK"
  [nick]
  (new-irc-cmd nil nil nil nil "NICK" nick nil))

(defn new-user-cmd
  "Creates IRC command USER"
  [username]
  (new-irc-cmd nil nil nil nil "USER" (lazy-seq [username "8" "*"]) username))

(defn new-join-cmd
  "Creates IRC command JOIN"
  [channel]
  (new-irc-cmd nil nil nil nil "JOIN" channel nil))

(defn new-privmsg-cmd
  "Creates IRC command PRIVMSG"
  [channel, final-arg]
  (new-irc-cmd nil nil nil nil "PRIVMSG" channel final-arg))
