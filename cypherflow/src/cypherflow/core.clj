(ns cypherflow.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn -main [& _]
  (with-open [rdr (io/reader "/resources/werewolves.cyf")]
    (->> (line-seq rdr)
         (map (fn [line] 
                (read-string (str "[" line "]"))))
         
        )))
