(ns irc-bot.core
  (:require [irc-command-parser.parser :refer [parse-irc-cmd]])
  (:require [irc-connection.connection :refer [create-irc-socket, init-connection, read-irc-cmd, send-irc-cmd]])
  (:require [module-loader.loader :refer [load-module]])
  (:require [modules.utils :refer [starts-with-module-prompt?]])
  (:require [module-command-parser.parser :refer [parse-module-cmd]])
  (:require [module-command.command :refer [module-cmd?, invoke-module-cmd]])
  (:require [config-parser.parser :refer [config-file, parse-host, parse-port, parse-nick, parse-username, parse-channel]])
  (:gen-class)
)

(def loaded-modules (vec (map load-module ["core"])))

(defn -main
  "Runs the IRC bot"
  [& args]
  (let 
    [
      host (parse-host config-file)
      port (parse-port config-file)
      nick (parse-nick config-file)
      username (parse-username config-file)
      channel (parse-channel config-file)
      sock (create-irc-socket host port)
    ]
    (init-connection sock nick username channel)
    (println (format "LOADED MODULES: %s" (vec (map (fn [module] (:module-name module)) loaded-modules))))
    (let
      [
        running? true
      ]
      (while running?
        (let 
          [
            rx-irc-cmd (read-irc-cmd sock) 
            parsed-rx-irc-cmd (parse-irc-cmd rx-irc-cmd)
            final-arg (:final-arg parsed-rx-irc-cmd)
            module-prompt "%"
          ]
          (println (format "PARSED RX IRC CMD: %s" parsed-rx-irc-cmd))
          (if (starts-with-module-prompt? final-arg module-prompt)
            (let 
              [
                module-cmd (:cmd-name (parse-module-cmd final-arg))
                module-cmd-args (:cmd-args (parse-module-cmd final-arg))
              ]
              (if (module-cmd? module-cmd loaded-modules)
                (send-irc-cmd sock (invoke-module-cmd module-cmd module-cmd-args loaded-modules))
                (println (format "%s, is not a valid module command." module-cmd))
              )
            )
          )
        )
      )
    )
  )
)


;;(defn me-irc-cmd
;;  []
;;  (cmd/fmt-for-tx (cmd/new-irc-cmd nil "PRIVMSG" "#sett" "\u0001ACTION coughs loudly\u0001")))
