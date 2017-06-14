(ns clojurednd.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]))

(deftest test-hit-dice-list
  (let [hit-dice-list "2⊠4⊠10⊠4⊠3⊠8⊠3"
        delim (->> 8864 (char) (str) (re-pattern))
        [hit-dice-count & numbers] (->> delim (s/split hit-dice-list) (map #(Integer/parseInt %)))
        hit-dice (mapv (fn [[number-of side-count _]]
                         {:number-of number-of
                          :side-count side-count})
                       (partition 3 numbers))
        ]
    (is (= hit-dice [{:number-of 4 :side-count 10} {:number-of 3 :side-count 8}]))
    ))

(deftest test-spell-list
  (let [spell-list "v1.61⊠0⊡4⊡3⊡3⊡3⊡2⊡1⊡0⊡0⊡0⊡⊠true⊡true⊡true⊡true⊡true⊡true⊡true⊡false⊡false⊡false⊡⊠0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡⊠0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡0⊡⊠0⊠11⊠false⊠0⊟Guidance⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Resistance⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Mending⊟⊟⊟⊟⊟⊟true⊟0⊡0⊟Produce Flame⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Cure Wounds⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Healing Word⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Fog Cloud⊟⊟⊟⊟⊟⊟true⊟0⊡1⊟Faerie Fire⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Barkskin *⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Spider Climb *⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Enhance Ability⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Pass Without A Trace⊟⊟⊟⊟⊟⊟true⊟0⊡2⊟Lesser Restoration⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Call Lightning *⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Plant Growth *⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Dispel Magic⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Protection From Energy⊟⊟⊟⊟⊟⊟true⊟0⊡3⊟Wind Wall⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Divination *⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Freedom of Movement *⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Hallucinatory Terrain⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Stone Skin⊟⊟⊟⊟⊟⊟true⊟0⊡4⊟Wall Of Fire⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Commune with Nature *⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Tree Stride *⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Antilife Shell⊟⊟⊟⊟⊟⊟true⊟0⊡5⊟Greater Restoration⊟⊟⊟⊟⊟⊟true⊟0⊡6⊟Heal⊟⊟⊟⊟⊟⊟true⊟0⊡"
        delims (map #(->> % (char) (str) (re-pattern)) [8864 8865 8863])
        elems (s/split spell-list (first delims))
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
                         [:cantrips :first :second :third :fourth :fifth :sixth :seventh :eighth :ninth])]
    (is (= spell-list [{:id :cantrips
                        :slots 0
                        :spells ["Guidance" "Resistance" "Mending" "Produce Flame"]}
                       {:id :first
                        :slots 4
                        :spells ["Cure Wounds" "Healing Word" "Fog Cloud" "Faerie Fire"]}
                       {:id :second
                        :slots 3
                        :spells ["Barkskin *" "Spider Climb *" "Enhance Ability" "Pass Without A Trace" "Lesser Restoration"]}
                       {:id :third
                        :slots 3
                        :spells ["Call Lightning *" "Plant Growth *" "Dispel Magic" "Protection From Energy" "Wind Wall"]}
                       {:id :fourth
                        :slots 3
                        :spells ["Divination *" "Freedom of Movement *" "Hallucinatory Terrain" "Stone Skin" "Wall Of Fire"]}
                       {:id :fifth
                        :slots 2
                        :spells ["Commune with Nature *" "Tree Stride *" "Antilife Shell" "Greater Restoration"]}
                       {:id :sixth
                        :slots 1
                        :spells ["Heal"]}]))))

(deftest test-weapon-list
  (let [weapon-str "4⊠Greatsword⊠5 feet⊠2⊠0⊠0⊠0⊠0⊠0⊠true⊠true⊠1⊠2⊠6⊠Longsword⊠5 feet⊠2⊠0⊠0⊠0⊠0⊠0⊠true⊠true⊠1⊠1⊠8⊠Light Crossbow⊠80/320⊠1⊠1⊠0⊠2⊠0⊠0⊠true⊠true⊠1⊠1⊠8⊠Handaxe⊠20/60⊠2⊠0⊠0⊠0⊠0⊠0⊠true⊠true⊠1⊠1⊠6"
        delim (->> 8864
                   (char)
                   (str)
                   (re-pattern))
        [number-of & weapon-data] (s/split weapon-str delim)
        number-of (Integer/parseInt number-of)
        weapons (loop [data weapon-data out []]
                  (if (empty? data)
                    (vec out)
                    (let [[weapon-name weapon-range damage-type num1 num2 num3 num4 num5 bool1 bool2 dice-count] (take 11 data)
                          data (drop 11 data)
                          num-of (* 2 (Integer/parseInt dice-count))
                          dice (mapv (fn [die]
                                       (let [[x y] (map #(Integer/parseInt %) die)]
                                         {:number-of x :dice-count y}))
                                     (partition 2 (take num-of data)))
                          data (drop num-of data)
                          new-weapon {:name (str weapon-name)
                                      :range (str weapon-range)
                                      :damage-type (nth [:bludgeoning :piercing :slashing] (Integer/parseInt damage-type))
                                      :damage-dice dice}]
                      (recur data (concat out [new-weapon])))))]
    (is (= weapons [{:name "Greatsword"
                     :range "5 feet"
                     :damage-type :slashing
                     :damage-dice [{:number-of 2
                                    :dice-count 6}]}
                    {:name "Longsword"
                     :range "5 feet"
                     :damage-type :slashing
                     :damage-dice [{:number-of 1
                                    :dice-count 8}]}
                    {:name "Light Crossbow"
                     :range "80/320"
                     :damage-type :piercing
                     :damage-dice [{:number-of 1
                                    :dice-count 8}]}
                    {:name "Handaxe"
                     :range "20/60"
                     :damage-type :slashing
                     :damage-dice [{:number-of 1
                                    :dice-count 6}]}]))))

(deftest test-ability-scores
  (let [ability-scores "14⊠13⊠19⊠9⊠13⊠10⊠true⊠false⊠true⊠false⊠false⊠false⊠0⊠0⊠0⊠0⊠0⊠0⊠0"
        delim (->> 8864 (char) (str) (re-pattern))
        [scores save-proficient bonus] (partition 6 (s/split ability-scores delim))
        abilities (mapv #(let [score (Integer/parseInt %1)]
                           {:label %4
                            :score score
                            :modifier (int (/ (- score 10) 2))
                            :save-proficient (Boolean/parseBoolean %2)
                            :bonus (Integer/parseInt %3)})
                        scores save-proficient bonus [:STR :DEX :CON :INT :WIS :CHA])]
    (println abilities)
    (is (= abilities [{:label :STR
                       :score 14
                       :modifier 2
                       :save-proficient true
                       :bonus 0}
                      {:label :DEX
                       :score 13
                       :modifier 1
                       :save-proficient false
                       :bonus 0}
                      {:label :CON
                       :score 19
                       :modifier 4
                       :save-proficient true
                       :bonus 0}
                      {:label :INT
                       :score 9
                       :modifier 0
                       :save-proficient false
                       :bonus 0}
                      {:label :WIS
                       :score 13
                       :modifier 1
                       :save-proficient false
                       :bonus 0}
                      {:label :CHA
                       :score 10
                       :modifier 0
                       :save-proficient false
                       :bonus 0}]))))

(deftest test-skill-list
  (let [skill-list "false⊠true⊠false⊠false⊠false⊠true⊠false⊠false⊠false⊠true⊠true⊠false⊠true⊠false⊠false⊠false⊠false⊠true⊠false⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false⊠false"
        delim (->> 8864 (char) (str) (re-pattern))
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
    (println skills)
    (println (count skills))
    (is (= skills [{:label :ATHLETICS
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :ACROBATICS
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :SLEIGHT-OF-HAND
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :STEALTH
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :ARCANA
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :HISTORY
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :INVESTIGATION
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :NATURE
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :RELIGION
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :ANIMAL-HANDLING
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :INSIGHT
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :MEDICINE
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :PERCEPTION
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :SURVIVAL
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :DECEPTION
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :INTIMIDATION
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :PERFORMANCE
                    :is-proficient false
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}
                   {:label :PERSUASION
                    :is-proficient true
                    :bonus 0
                    :bools1 false
                    :bools2 false
                    :bools3 false}]))))

(deftest test-class-data
  (let [class-data "Fighter⊡Champion⊡4⊡-1⊠Ranger⊡Hunter⊡3⊡1⊟⊟Resource⊡Resource⊡0⊡0⊡0⊡2⊡0⊡0⊡-1⊡0⊠Action Surge⊡⊡1⊡1⊡0⊡2⊡0⊡0⊡303⊡0⊟Dual Wielder⊟fighting style⊡Archery⊟⊟⊟Human⊟Human Variant⊟Entertainer⊟0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠⊟0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠0⊠⊟20⊠20⊠20⊠20⊠20⊠20⊠⊟0⊟2⊟0⊟1⊟1⊟0"
        delims (map #(->> % (char) (str) (re-pattern)) [8864 8865 8863])
        [class1 class2 features & extras] (s/split class-data (first delims))
        ]
    (reduce #(println %2) {} (s/split class1 (second delims)))
    (reduce #(println %2) {} (s/split class2 (second delims)))
    (reduce #(println %2) {} (s/split features (last delims)))
    ))

(deftest test-note-list
  (let [note-list "Human Features:\n• One extra skill proficiency of your choosing\n• One bonus feat of your choosing\nNoble Feature:\n• You can secure an audience with a local noble\nClass Features:\n• Heal 1d10 + level hit points as a bonus action (once between rests)\n• Fighting Style - Archery: +2 to attack with ranged weapons\n• Dual Wielder: You can draw/stow two weapons per round, you can dual wield non-light weapons, and you gain +1 AC while dual wielding\n• Gain advantage on survival and Int checks concerning your chosen creature type, and you know a language spoken by those creatures\n• Gain advantage on Wis and Int checks concerning your chosen terrain, and bonuses while traveling through that terrain (see rules)\n• Take an extra action once between rests\n• Fighter Archetype: Champion\n• Crit on 19\n• You can sacrifice a spell slot as an action to determine if certain creature types are within 1 mile (6 miles if favored terrain) (see rules)\n• Big Game Hunter - You can use a reaction to attack a creature of at least Large size that attacks you\n⊠Light Armor, Medium Armor, Heavy Armor, Shields\n⊠Simple Weapons, Martial Weapons\n⊠One type of gaming set\n⊠Common\nExtra Language\nOne Extra Language\n⊠martial weapon\nshield\nlight crossbow + 20 bolts OR two hand axes\ndungeoneer's pack OR explorer's pack\nset of fine clothes\nsignet ring\nscroll of pedigree\npurse\nchain mail\n⊠⊠Fighter 4, Ranger 3⊠Human⊠Noble⊠⊠⊠⊠⊠⊠Baron Crispian Dale⊠Fighter 4, Ranger 3⊠0⊠0⊠0⊠25⊠0⊠0"
        delim (->> 8864 (char) (str) (re-pattern))
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
    (is (= doc-text {:name "Baron Crispian Dale"
                     :race "Human"
                     :classes [{:class "Fighter"
                                :level 4}
                               {:class "Ranger"
                                :level 3}]
                     :background "Noble"
                     :features {"Human Features:" ["• One extra skill proficiency of your choosing"
                                                   "• One bonus feat of your choosing"]
                                "Noble Feature:" ["• You can secure an audience with a local noble"]
                                "Class Features:" ["• Heal 1d10 + level hit points as a bonus action (once between rests)"
                                                   "• Fighting Style - Archery: +2 to attack with ranged weapons"
                                                   "• Dual Wielder: You can draw/stow two weapons per round, you can dual wield non-light weapons, and you gain +1 AC while dual wielding"
                                                   "• Gain advantage on survival and Int checks concerning your chosen creature type, and you know a language spoken by those creatures"
                                                   "• Gain advantage on Wis and Int checks concerning your chosen terrain, and bonuses while traveling through that terrain (see rules)"
                                                   "• Take an extra action once between rests"
                                                   "• Fighter Archetype: Champion"
                                                   "• Crit on 19"
                                                   "• You can sacrifice a spell slot as an action to determine if certain creature types are within 1 mile (6 miles if favored terrain) (see rules)"
                                                   "• Big Game Hunter - You can use a reaction to attack a creature of at least Large size that attacks you"]}
                     :equiptment ["martial weapon"
                                  "shield"
                                  "light crossbow + 20 bolts OR two hand axes"
                                  "dungeoneer's pack OR explorer's pack"
                                  "set of fine clothes"
                                  "signet ring"
                                  "scroll of pedigree"
                                  "purse"
                                  "chain mail"]
                     :proficiencies {:armor ["Light Armor" "Medium Armor" "Heavy Armor" "Shields"]
                                     :weapon ["Simple Weapons" "Martial Weapons"]
                                     :tools ["One type of gaming set"]
                                     :languages ["Common" "Extra Language" "One Extra Language"]}
                     :personality {:alignment ""
                                   :traits ""
                                   :ideals ""
                                   :bonds ""
                                   :flaws ""}
                     :purse {:platinum 0
                             :gold 25
                             :electrum 0
                             :silver 0
                             :copper 0}
                     :notes ""}))))
