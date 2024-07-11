(ns cypherflow.parse
  (:require
   [clojure.pprint :as pp]
   [clojure.string :as str]))

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
   [#"> =" ">="]
   [#"[.]\d+" #(str "0" (str/trim %))]
   [#"REM (.+)" #(str "REM \"" (second %) "\"")]])

(defn parse [lines]
  (->> lines
       (filter #(not-empty (str/trim %)))
       (map (fn [line]
              (let [replaced (reduce #(str/replace %1 (first %2) (second %2)) line replacements)
                    text (str/trim (str "[" replaced "]"))]
                (println text)
                (let [code-line (read-string text)]
                  (pp/pprint code-line)
                  code-line))))
       (vec)))