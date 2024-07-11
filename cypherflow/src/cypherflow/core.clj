(ns cypherflow.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(def reserved
  {:comparators '#{< > = <>}
   :operators '#{+ - * /}
   :functions '#{RND INT}})

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
   [#"[.]\d+" #(str "0" (str/trim %))]])

(defn -main [& _]
  (with-open [rdr (io/reader "./resources/werewolves.cyf")
              w (io/writer "./resources/werewolves.edn" :encoding "UTF-8")]
    (let [code (->> (line-seq rdr)
                    (filter #(not-empty (str/trim %)))
                    (map (fn [line]
                           (let [replaced (reduce #(str/replace %1 (first %2) (second %2)) line replacements)
                                 text (str/trim (str "[" replaced "]"))]
                             (println text)
                             (let [code-line (read-string text)]
                               (pp/pprint code-line)
                               code-line))))
                    (vec))
          symbols (reduce 
                   (fn [outList line]
                     (reduce 
                      #(if (symbol? %2) (conj %1 %2) %1)
                      outList
                      line))
                   (sorted-set)
                   code)
          command-words (reduce
                         #(if (or (> 3 (count %2)) (not= (nth %2 2) (symbol "=")))
                            (do
                              (pp/pprint { :second (second %2) :line %2})
                              (conj %1 (second %2)))
                            %1)
                         (sorted-set)
                         code)]
      (println (count code))
      (.write w (with-out-str (pp/pprint code)))
      (pp/pprint command-words)
      (pp/pprint (set/difference symbols command-words))
      )))
