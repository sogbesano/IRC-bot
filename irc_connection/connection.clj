(ns irc-connection.connection
  (:require [clj-sockets.core :refer [create-socket, write-to, read-line]])
  (:require [irc-command.command :refer [fmt-for-tx, new-irc-cmd, new-nick-cmd, new-user-cmd, new-join-cmd]])
  (:gen-class)
)

(defn create-irc-socket
  "Creates a socket connected to the IRC server using host and port"
  [host, port]
  (create-socket host port)
)

(defn send-irc-cmd
  "Writes the irc-cmd(s) to the IRC socket"
  [socket, irc-cmd]
  (if (seq? irc-cmd) 
    (doseq [cmd irc-cmd] (write-to socket (fmt-for-tx cmd))) 
    (write-to socket (fmt-for-tx irc-cmd))
  )
) 

(defn read-irc-cmd
  "Reads a line from the IRC socket and returns it"
  [socket]
  (read-line socket)
)

(defn init-connection
  "Sends initial IRC commands: NICK, USER and JOIN to the IRC server via a connected socket"
  [socket, nick, username, channel]
  (let 
    [
      nick-cmd (new-nick-cmd nick) 
      user-cmd (new-user-cmd username) 
      join-cmd (new-join-cmd channel)
    ]
    (send-irc-cmd socket (lazy-seq [nick-cmd user-cmd join-cmd]))
  )
) 


