(ns cypherflow.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [cypherflow.parse :as cfp])
  (:gen-class))

(defn -main [& _]
  (with-open [rdr (io/reader "./resources/werewolves.edn")]
    (let [code (edn/read rdr)]
      )))
      
(defn parse-and-write-file []
  (with-open [rdr (io/reader "./resources/werewolves.cyf")
              w (io/writer "./resources/werewolves.edn" :encoding "UTF-8")]
    (let [code (cfp/parse (line-seq rdr))]
      (println (count code))
      (.write w (with-out-str (pp/pprint code))))))