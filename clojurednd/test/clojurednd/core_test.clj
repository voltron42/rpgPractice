(ns clojurednd.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]))

(deftest test-hit-dice-list
  (let [hit-dice-list "2⊠4⊠10⊠4⊠3⊠8⊠3"]
    (->> 8864
         (char)
         (str)
         (re-pattern)
         (s/split hit-dice-list)
         (map #(Integer/parseInt %))
         (= [2 4 10 4 3 8 3])
         (is))))

(deftest test-spell-list
  (let [spell-list "v1.61⊠0⊡4⊡3⊡3⊡3⊡2⊡1⊡0⊡0⊡0⊡⊠true⊡true⊡true⊡true⊡true⊡true⊡true⊡false⊡false⊡false⊡⊠0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡⊠0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡⊠0⊠11⊠false⊠0⊟Guidance⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Resistance⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Mending⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Produce Flame⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Cure Wounds⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Healing Word⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Fog Cloud⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Faerie Fire⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Barkskin *⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Spider Climb *⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Enhance Ability⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Pass Without A Trace⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Lesser Restoration⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Call Lightning *⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Plant Growth *⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Dispel Magic⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Protection From Energy⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Wind Wall⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Divination *⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Freedom of Movement *⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Hallucinatory Terrain⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Stone Skin⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Wall Of Fire⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Commune with Nature *⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Tree Stride *⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Antilife Shell⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Greater Restoration⊟⊟⊟⊟⊟⊟true⊟0⊡6⊟Heal⊟⊟⊟⊟⊟⊟true⊟0⊡"
        delims (map #(->> % (char) (str) (re-pattern)) [8864 8865 8863])
        elems (s/split spell-list (first delims))
        ]
    (reduce #(println %2) {} elems)
    (reduce #(println %2) {} (s/split (last elems) (second delims)))
    ))