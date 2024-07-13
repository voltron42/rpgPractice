(ns cypherflow.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [cypherflow.parse :as cfp])
  (:gen-class))

(defn -main [& _]
  (with-open [rdr (java.io.PushbackReader. (io/reader "./resources/werewolves.edn"))
              w (io/writer "./resources/werewolves-model.edn" :encoding "UTF-8")]
    (let [code (edn/read rdr)
          {lines :lines errors :errors} (reduce
                 (fn [{lines :lines errors :errors} line]
                   (let [[line-model error] (try
                                              [(cfp/model line)]
                                              (catch clojure.lang.ExceptionInfo e
                                                [nil (ex-data e)]))]
                     (if (some? error)
                       {:lines lines :errors (conj errors error)}
                       {:lines (conj lines line-model) :errors errors}
                       )))
                 {:lines [] :errors []}
                 code)]
      (pp/pprint {:lines (count lines) :errors (count errors)})
      (if (empty? errors)
        (.write w (with-out-str (pp/pprint lines)))
        (pp/pprint (first errors))))))
      
(defn parse-and-write-file []
  (with-open [rdr (io/reader "./resources/werewolves.cyf")
              w (io/writer "./resources/werewolves.edn" :encoding "UTF-8")]
    (let [code (cfp/parse (line-seq rdr))]
      (println (count code))
      (.write w (with-out-str (pp/pprint code))))))