(ns clojurednd.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip])
  (:import (java.io File)))

(def delimiters [8864 8865 8863])

(def parse {:int
                  #(Integer/parseInt %)
            :bool
                  #(Boolean/parseBoolean %)})

(def headers {
              :abilities [:STRENGTH :DEXTERITY :CONSTITUTION :INTELLIGENCE :WISDOM :CHARISMA]
              :abilityFields [:score :isSaveProficient :bonus]
              :skills [:ATHLETICS :ACROBATICS :SLEIGHT-OF-HAND :STEALTH :ARCANA :HISTORY :INVESTIGATION :NATURE :RELIGION :ANIMAL-HANDLING :INSIGHT :MEDICINE :PERCEPTION :SURVIVAL :DECEPTION :INTIMIDATION :PERFORMANCE :PERSUASION :UNKNOWN]
              :skillFields [:isProficient :bonus :check1 :check2 :check3]
              :weapon [:name :range :damageType :attackAbility :rangedType :handedType :magicDamageBonus :miscDamageBonus :addAbilityMod :proficient]
              :dice [:numberOf :sideCount]
              :hitDice [:numberOf :sideCount :remaining]
              }
  )

(defn nilSafe [func] (fn [value] (if (nil? value) nil (func value))))

(defn flipMap [mymap] (reduce (fn [out [key vals]] (reduce (fn [prev val] (assoc prev val key)) out vals)) {} mymap))

(defn buildSplitter [delimiter] (fn [^String subject]
                                  (if (empty? subject) [] (clojure.string/split subject delimiter))
                                  ))

(defn charDelim [delimInt] (-> delimInt (char) (str) (re-pattern)))

(defn objectBuilder [fields] (fn [values] (reduce (fn [obj [k v]] (assoc obj k v)) {} (mapv vector fields values))))

(defn embedKey [[key func]]
  #(do
     ;;(println key)
     (func (% key))
     )
  )

(defn mapObj [func obj] (reduce (fn [out [k v]] (assoc out k (func (vector k v)))) {} obj))

(defn mapSplit [delim func] #(map func ((buildSplitter delim) %)))

(defn buildFieldFunc [fieldFuncs]
  (if (empty? fieldFuncs)
    identity
    (fn [obj] (reduce
                (fn [out funcObj]
                  (reduce
                    (fn [prev [k f]]
                      (if (nil? f)
                        (dissoc prev k)
                        (assoc prev k (f prev))
                        )
                      )
                    out
                    funcObj)
                  )
                obj
                fieldFuncs))
    )
  )

(defn listBuilder [fields & fieldFuncs]
  (let [fieldFunc (buildFieldFunc fieldFuncs)
        obBuilder (objectBuilder fields)
        fieldCount (count fields)]
    (fn [mylist]
      (map
        fieldFunc
        (let [count ((parse :int) (first mylist))
              values (rest mylist)]
          (loop [out [] step count vals values]
            (if (= 0 step)
              out
              (recur (concat out (vector (obBuilder (take fieldCount vals)))) (dec step) (drop fieldCount vals))
              )
            )
          )
        )
      )
    )
  )

(defn varListBuilder
  ([fields listName listFields] (varListBuilder fields listName listFields {}))
  ([fields listName listFields {:keys [base list]}]
   (let [baseFieldFunc (buildFieldFunc base)
         listFieldFunc (buildFieldFunc list)
         listBuild (listBuilder listFields)
         listFieldCount (count listFields)
         obBuilder (objectBuilder fields)
         fieldCount (count fields)]
     (fn buildList [mylist]
       (let [listCount ((parse :int) (first mylist))
             values (rest mylist)]
         (loop [out [] step listCount vals values]
           (if (= 0 step)
             out
             (let [baseObj (baseFieldFunc (obBuilder (take fieldCount vals)))
                   objList (map listFieldFunc (listBuild (drop fieldCount vals)))
                   listValCount (+ 1 (* listFieldCount (count objList)))
                   fullObj (assoc baseObj listName objList)
                   dropCount (+ fieldCount listValCount)]
               (recur (concat out (vector fullObj)) (dec step) (drop dropCount vals))
               )
             )
           )
         )
       )
     )
    )
  )

(defn listInverter [fields objNames & fieldFuncs]
  (let [buildObj (objectBuilder fields)
        fieldFunc (buildFieldFunc fieldFuncs)]
    (fn [values]
      (let [objCount (count objNames)
            flipmix (loop [out (map vector (take objCount values))
                           vals (drop objCount values)
                           ]
                      (if (< (count vals) objCount)
                        out
                        (recur (mapv conj out (take objCount vals)) (drop objCount vals))
                        )
                      )
            ]
        (apply vector (interleave objNames (map fieldFunc (map buildObj flipmix))))
        )
      )
    )
  )

(defn processContents [xmlbody]
  (reduce
    (fn [mymap {:keys [tag content]}]
      (assoc mymap tag (first content)))
    {}
    (-> xmlbody (first) (:content))))

;;[:noteList :classResource :classData :spellList]

(def charFuncs (->> (merge
                      (flipMap
                        {(nilSafe (parse :int))                             [:version :initMiscMod :improvedInitiative :currentHealth :maxHealth :currentTempHP :armorBonus :shieldBonus :miscArmorBonus :maxDex :proficiencyBonus :miscSpellAttackBonus :miscSpellDCBonus :castingStatCode :offenseAbilityDisplay :deathSaveSuccesses :deathSaveFailures :baseSpeed :speedMiscMod :raceCode :subraceCode :backgroundCode :pagePosition0 :pagePosition1 :pagePosition2 :pagePosition3 :pagePosition4 :unarmoredDefense :featCode]
                         (mapSplit #"," (nilSafe (parse :int)))             [:multiclassFeatures]
                         (mapSplit (charDelim 8864) (nilSafe (parse :int))) [:classResource]
                         })
                      {:showDeathSaves (nilSafe (parse :bool))}
                      ;;(comment
                      (mapObj #(mapSplit (charDelim 8864) %)
                              {
                               :hitDiceList   (listBuilder
                                                (:hitDice headers)
                                                (->> {(nilSafe (parse :int)) [:numberOf :sideCount :remaining]} (flipMap) (mapObj embedKey))
                                                )
                               :abilityScores (listInverter
                                                (:abilityFields headers)
                                                (:abilities headers)
                                                (mapObj embedKey (flipMap {(nilSafe (parse :int)) [:score :bonus]}))
                                                (mapObj embedKey
                                                        {:isSaveProficient (nilSafe (parse :bool))
                                                         :modifier         #(int (/ (- % 10) 2))
                                                         }))
                               :skillInfo     (listInverter
                                                (:skillFields headers)
                                                (:skills headers)
                                                (mapObj embedKey (flipMap {(nilSafe (parse :bool)) [:isProficient :check1 :check2 :check3]}))
                                                (mapObj embedKey {:bonus (nilSafe (parse :int))})
                                                )
                               :weaponList    (varListBuilder
                                                (:weapon headers)
                                                :damageDice
                                                (:dice headers)
                                                {
                                                 :list (->> {(nilSafe (parse :int)) [:numberOf :sideCount]} (flipMap) (mapObj embedKey))
                                                 :base (->> {(nilSafe (parse :int))  [:attackAbility :damageType :miscDamageBonus :magicDamageBonus :rangedType :handedType]
                                                             (nilSafe (parse :bool)) [:addAbilityMod :proficient]}
                                                            (flipMap)
                                                            (mapObj embedKey)
                                                            )
                                                 }
                                                )
                               }
                              )
                      )
                    ;;)
                    (mapObj embedKey)
                    (vector)
                    (buildFieldFunc)))

(defn -main [& args]
  (try
    (let [files (-> "resources/charsheets" (File.) (.listFiles))
          loadFile (fn [file]
                     (let [filename (-> file (.getName))]
                       ;;(println filename)
                       {:filename filename
                        :contents (-> file
                                      (.getAbsoluteFile)
                                      (xml/parse)
                                      (zip/xml-zip)
                                      (processContents)
                                      (charFuncs)
                                      )
                        }
                     )
      )
          output (map loadFile files)
]
      (println (->> output (first) (:contents) (:abilityScores) (type)))
      ;;(println output)
    ;;(spit "characters.edn" output)
    )
    (catch Exception e
      (-> e (.getMessage) (println))
      (-> e (.printStackTrace))
      )
    )
  )
