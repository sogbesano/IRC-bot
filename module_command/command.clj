(ns module-command.command
  (:gen-class))

(defn get-loaded-modules-keys
  "Returns all the module keys in loaded modules except :module-name"
  [loaded-modules]
  (let [all-modules (vec (map (fn [module] (keys module)) loaded-modules))]
    (vec (for [module all-modules module-key (remove (fn [mod-key] (= mod-key :module-name)) module)] module-key))))

(defn module-cmd?
  "Returns true if module command is in loaded modules"
  [module-cmd-name, loaded-modules]
  (not (empty? (filter (fn [module-key] (= module-key (keyword module-cmd-name))) (get-loaded-modules-keys loaded-modules)))))

(defn get-all-module-cmds
  "Gets all module commands from loaded-modules"
  [loaded-modules]
  (vec (for [module loaded-modules] (select-keys module (for [[k v] module :when (not (= :module-name k))] k)))))

(defn get-module-cmd
  "Gets the module command object from loaded-modules"
  [module-cmd-name loaded-modules]
  (first (for [module (get-all-module-cmds loaded-modules)] 
            (select-keys module (for [[k v] module :when (= (keyword module-cmd-name) k)] k)))))

(defn invoke-module-cmd
  "Invokes the module command"
  [module-cmd-name, module-cmd-args, loaded-modules]
  (let [module-cmd ((keyword module-cmd-name) (get-module-cmd module-cmd-name loaded-modules))]
    (println (format "MODULE-CMD-ARGS: %s" module-cmd-args))
    (apply module-cmd module-cmd-args)))


      
