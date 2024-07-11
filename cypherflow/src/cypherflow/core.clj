(ns cypherflow.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.set :as set]
            [cypherflow.parse :as cfp])
  (:gen-class))

(def reserved
  {:comparators '#{< > = <>}
   :operators '#{+ - * /}
   :conjunctions '#{AND OR}
   :functions '#{RND INT A}
   :command-words '#{THEN ELSE TO}})

(defn -main [& _]
  (with-open [rdr (io/reader "./resources/werewolves.cyf")
              w (io/writer "./resources/werewolves.edn" :encoding "UTF-8")]
    (let [code (cfp/parse (line-seq rdr))
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
                              ;;(pp/pprint { :second (second %2) :line %2})
                              (conj %1 (second %2)))
                            %1)
                         (sorted-set)
                         code)
          variables (reduce
                         #(if (and (<= 3 (count %2)) (= (nth %2 2) (symbol "=")))
                            (do
                              ;;(pp/pprint { :second (second %2) :line %2})
                              (conj %1 (second %2)))
                            %1)
                         (sorted-set)
                         code)
          known-reserved (set/union (apply set/union (vals reserved)) command-words variables)
          unknown (set/difference symbols known-reserved)
          all-symbols (assoc
                       reserved
                       :command-words (set/union (:command-words reserved) command-words)
                       :variables variables
                       :unknown unknown)]
      (println (count code))
      (.write w (with-out-str (pp/pprint code)))
      (pp/pprint all-symbols))))
      
