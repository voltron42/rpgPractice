(ns clojurednd.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip])
  (:import (java.io File)))

(def delimiter (char 8864))

(def resolverMap
  ((fn []
     (let [
           flip {
                 #(if (nil? %) nil (Integer/parseInt %)) [
                    :version
                    :initMiscMod
                    :improvedInitiative
                    :currentHealth
                    :maxHealth
                    :currentTempHP
                    :armorBonus
                    :shieldBonus
                    :miscArmorBonus
                    :maxDex
                    :proficiencyBonus
                    :miscSpellAttackBonus
                    :miscSpellDCBonus
                    :castingStatCode
                    :offenseAbilityDisplay
                    :deathSaveSuccesses
                    :deathSaveFailures
                    :baseSpeed
                    :speedMiscMod
                    :raceCode
                    :subraceCode
                    :backgroundCode
                    :pagePosition0
                    :pagePosition1
                    :pagePosition2
                    :pagePosition3
                    :pagePosition4
                    :unarmoredDefense
                    :featCode
                    ]
                 #(if (nil? %) nil (Boolean/parseBoolean %)) [:showDeathSaves]
                 #(clojure.string/split % (re-pattern (str delimiter))) [
                      :classData
                      :weaponList
                      :abilityScores
                      :skillInfo
                      :spellList
                      :noteList
                      :hitDiceList
                      :classResource
                      ]
                 #(if (empty? %) "" (clojure.string/split % #",")) [:multiclassFeatures]
                 }
           ]
       (reduce (fn [out [k vs]] (reduce (fn [prev v] (assoc prev v k)) out vs)) {} flip)
       )
     ))
  )

(defn processContents [xmlbody]
  (reduce
    (fn [mymap {:keys [tag content]}]
      (do
        (println tag)
        (assoc mymap tag (
                           (if (contains? resolverMap tag)
                             (resolverMap tag)
                             identity
                             )
                           (first content)))
        )
      )
    {}
    (-> xmlbody (first) (:content))
    )
  )

(defn -main [& args]
  (let [
        files (-> "resources" (File.) (.listFiles))
        loadFile (fn [file]
                   (let [
                         filename (-> file (.getName))
                         ]
                     (println filename)
                     {
                      :filename filename
                      :contents (-> file (.getAbsoluteFile) (xml/parse) (zip/xml-zip) (processContents))
                      }
                     )
                   )
        ]
    (spit "characters.edn" (apply vector (map loadFile files)))
    )
  )