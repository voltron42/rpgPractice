(ns clojurerpg.flow
  (:require [clojure.edn :as edn]))

(defn runflow [flow]
  )

(defn -main [& args]
  (when (empty? args) (throw "must pass in a path to the game file"))
  (let
    [
     filename (first args)
     data (edn/read-string (slurp filename))
     state {}
     ]
    (runflow data)
    )
  )