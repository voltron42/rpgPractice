(ns clojurednd.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as s]
            [clojure.pprint :as p])
  (:import (java.io File ByteArrayInputStream FileInputStream)))

(def ^:private delims (map #(->> % (char) (str) (re-pattern)) [8864 8865 8863]))

(defn- parse-hit-dice [hit-dice-list]
  (let [delim (->> 8864 (char) (str) (re-pattern))
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
                                       {:level level :name (str name )}) spells)))
        spell-list (do (println spells)
                       (mapv (fn [slot available index id]
                               {:id id :slots slot :spells (mapv :name (spells (str index)))})
                             (map #(Integer/parseInt %) slots)
                             (filter #(Boolean/parseBoolean %) level-available)
                             (range)
                             [:cantrips :first :second :third :fourth :fifth :sixth :seventh :eighth :ninth]))]
    spell-list))

(defn- parse-weapon-list [weapon-str]
  (let [delim (first delims)
        [_ & weapon-data] (s/split weapon-str delim)
        weapons (loop [data weapon-data out []]
                  (if (empty? data)
                    (vec out)
                    (let [[weapon-name weapon-range damage-type num1 num2 num3 num4 num5 bool1 bool2 dice-count] (take 11 data)
                          data (drop 11 data)
                          num-of (* 2 (Integer/parseInt dice-count))
                          dice (mapv (fn [die]
                                       (let [[x y] (map #(Integer/parseInt %) die)]
                                         {:number-of x :side-count y}))
                                     (partition 2 (take num-of data)))
                          data (drop num-of data)
                          new-weapon {:name (str weapon-name)
                                      :range (str weapon-range)
                                      :damage-type (let [damage (Integer/parseInt damage-type)]
                                                     (if (or (> 0 damage) (< 2 damage))
                                                       damage
                                                       (nth [:bludgeoning :piercing :slashing] damage)))
                                      :damage-dice dice}]
                      (recur data (concat out [new-weapon])))))]
    weapons))

(defn- parse-ability-scores [ability-scores]
  (let [delim (first delims)
        [scores save-proficient bonus] (partition 6 (s/split ability-scores delim))
        abilities (mapv #(let [score (Integer/parseInt %1)]
                           {:label %4
                            :score score
                            :modifier (int (/ (- score 10) 2))
                            :save-proficient (Boolean/parseBoolean %2)
                            :bonus (Integer/parseInt %3)})
                        scores save-proficient bonus [:STR :DEX :CON :INT :WIS :CHA])]
    abilities))

(defn- parse-skill-list [skill-list]
  (let [delim (->> 8864 (char) (str) (re-pattern))
        [skill-proficiency bonus bools1 bools2 bools3] (partition 19 (s/split skill-list delim))
        skills (mapv #(-> {:label %6
                           :is-proficient (Boolean/parseBoolean %1)
                           :bonus (Integer/parseInt %2)
                           :bools1 (Boolean/parseBoolean %3)
                           :bools2 (Boolean/parseBoolean %4)
                           :bools3 (Boolean/parseBoolean %5)})
                     skill-proficiency bonus bools1 bools2 bools3
                     [:ATHLETICS :ACROBATICS :SLEIGHT-OF-HAND :STEALTH :ARCANA
                      :HISTORY :INVESTIGATION :NATURE :RELIGION :ANIMAL-HANDLING
                      :INSIGHT :MEDICINE :PERCEPTION :SURVIVAL :DECEPTION
                      :INTIMIDATION :PERFORMANCE :PERSUASION])]
    skills))

(defn- parse-note-list [note-list]
  (let [delim (first delims)
        bullet 8226
        [features armor-proficiencies weapon-proficiencies tool-proficiencies
         languages equiptment alignment classes race background traits ideals
         bonds flaws notes char-name classes-match copper silver electrum gold
         platinum] (s/split note-list delim)
        feature-list (loop [out {} f-list (s/split features #"\n")]
                       (if (empty? f-list)
                         out
                         (let [key (first f-list)
                               remaining (rest f-list)
                               bullets (vec (take-while #(= bullet (int (first %))) remaining))
                               remaining (drop (count bullets) remaining)]
                           (recur (assoc out key bullets) remaining))))
        doc-text {:name char-name
                  :race race
                  :classes (mapv #(let [[class level]
                                        (s/split % #" ")]
                                    {:class class
                                     :level (Integer/parseInt level)})
                                 (s/split classes #", "))
                  :background background
                  :features feature-list
                  :equiptment (s/split equiptment #"\n")
                  :proficiencies {:armor (s/split (s/trim armor-proficiencies) #", ")
                                  :weapon (s/split (s/trim weapon-proficiencies) #", ")
                                  :tools (s/split (s/trim tool-proficiencies) #", ")
                                  :languages (s/split (s/trim languages) #"\n")}
                  :personality {:alignment alignment
                                :traits traits
                                :ideals ideals
                                :bonds bonds
                                :flaws flaws}
                  :purse {:platinum (Integer/parseInt platinum)
                          :gold (Integer/parseInt gold)
                          :electrum (Integer/parseInt electrum)
                          :silver (Integer/parseInt silver)
                          :copper (Integer/parseInt copper)}
                  :notes notes}]
    doc-text))

(def ^:private op-map (merge {:weaponList parse-weapon-list
                              :abilityScores parse-ability-scores
                              :noteList parse-note-list
                              :skillInfo parse-skill-list
                              :spellList parse-spell-list
                              :hitDiceList parse-hit-dice}
                             (reduce (fn [out [k v]]
                                       (reduce #(assoc %1 %2 v) out k))
                                     {}
                                     {[:version
                                       :initMiscMod
                                       :improvedInitiative
                                       :maxHealth
                                       :armorBonus
                                       :shieldBonus
                                       :miscArmorBonus
                                       :maxDex
                                       :proficiencyBonus
                                       :miscSpellAttackBonus
                                       :miscSpellDCBonus
                                       :castingStatCode
                                       :offenseAbilityDisplay
                                       :baseSpeed
                                       :speedMiscMod
                                       :raceCode
                                       :subraceCode
                                       :backgroundCode
                                       :unarmoredDefense] #(Integer/parseInt %)
                                      [:showDeathSaves] #(Boolean/parseBoolean %)
                                      [:movementMode
                                       :featCode
                                       :classResource
                                       :multiclassFeatures] identity})))

(defn- model-character-data [char]
  (->> char
       (first)
       (:content)
       (reduce (fn [{:keys [op non-op]} {:keys [tag content]}]
                 (let [content (first content)]
                   (if (contains? op-map tag)
                     {:op (assoc op tag ((op-map tag) content))
                      :non-op non-op}
                     {:op op
                      :non-op (assoc non-op tag content)})))
               {:op {} :non-op {}})))

(defn -main [& args]
  (try
    (let [files (->> "resources/charsheets" (File.) (.listFiles))
          modeled-chars (reduce #(assoc %1 (.getName %2) (let [stream (FileInputStream. ^File %2)
                                                doc (zip/xml-zip (xml/parse stream))]
                                            (model-character-data doc))) {} files)]
      (spit "resources/chardocs/chars.edn" (with-out-str (p/pprint modeled-chars))))
    (catch Exception e
      (-> e (.getMessage) (println))
      (-> e (.printStackTrace)))))
