(ns cypherflow.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.string :as str])
  (:gen-class))

(def replacements
  [[#"\:" " :_ "]
   [#"\;" " "]
   [#"\*" " * "]
   [#"\/" " / "]
   [#"\>" " > "]
   [#"\<" " < "]
   [#"\=" " = "]
   [#"\+" " + "]
   [#"\-" " - "]
   [#"  " " "]
   [#"  " " "]
   [#"< >" "<>"]
   [#"< =" "<="]
   [#"> =" ">="]])

(defn -main [& _]
  (with-open [rdr (io/reader "./resources/werewolves.cyf")]
    (let [code (->> (line-seq rdr)
                    (filter #(not-empty (str/trim %)))
                    (map (fn [line]
                           (let [replaced (reduce #(str/replace %1 (first %2) (second %2)) line replacements)
                                 text (str/trim (str "[" replaced "]"))]
                             (println text)
                             (let [code-line (read-string text)]
                               (pp/pprint code-line)
                               code-line))))
                    (vec))]
      (println (count code)))))
