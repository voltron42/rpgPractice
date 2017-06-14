(ns clojurednd.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as s])
  (:import (java.io File)))

(def ^:private delims (map #(->> % (char) (str) (re-pattern)) [8864 8865 8863]))

(defn- parse-hit-dice [hit-dice-list]
  (let [hit-dice-list "2⊠4⊠10⊠4⊠3⊠8⊠3"
        delim (->> 8864 (char) (str) (re-pattern))
        [hit-dice-count & numbers] (->> delim (s/split hit-dice-list) (map #(Integer/parseInt %)))
        hit-dice (mapv (fn [[number-of side-count _]]
                         {:number-of number-of
                          :side-count side-count})
                       (partition 3 numbers))]
    hit-dice))

(defn- parse-spell-list [spell-list]
  (let [elems (s/split spell-list (first delims))
        [version slots level-available _ _ _ caster-level _ spells] elems
        [slots level-available spells] (do
                                         (map #(s/split % (second delims)) [slots level-available spells]))
        spells (do
                 (group-by :level (map
                                    #(let [[level name & _] (s/split % (last delims))]
                                       {:level (Integer/parseInt level) :name (str name )}) spells)))
        spell-list (mapv (fn [slot available index id]
                           {:id id :slots slot :spells (mapv :name (spells index))})
                         (map #(Integer/parseInt %) slots)
                         (filter #(Boolean/parseBoolean %) level-available)
                         (range)
                         [:cantrips :first :second :third :fourth :fifth :sixth :seventh :eighth :ninth])]))

(defn- parse-weapon-list [weapon-list])

(defn- parse-ability-scores [ability-scores])

(defn- parse-skill-list [skill-list])

(defn- parse-note-list [note-list])


(defn -main [& args]
  (try
    (let [files (-> "resources/charsheets" (File.) (.listFiles))]
      (reduce #(println %2) {} files))
    (catch Exception e
      (-> e (.getMessage) (println))
      (-> e (.printStackTrace))
      )
    )
  )
