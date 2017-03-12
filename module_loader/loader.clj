(ns module-loader.loader
  (:require [clojure.java.io :refer [file]])
  (:gen-class))

(defn module?
  "Returns true if module-name exists in modules dir"
  [module-name]
  (let [dir (file (format "%s/src/modules/%s" (.getCanonicalPath (file ".")) module-name))]
    (.exists dir)))

(defn load-module
  "Loads a module"
  [module-name]
  (if (module? module-name)
    (var-get (load-file (format "%s/src/modules/%s/%s.clj" (.getCanonicalPath (file ".")) module-name module-name)))
    (println (format "Unable to find module: %s" module-name)))
  )

