(ns cypherflow.parse
  (:require [clojure.string :as str]))

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
              (let [replaced (reduce #(str/replace %1 (first %2) (second %2)) line replacements)]
                (read-string (str/trim (str "[" replaced "]"))))))
       (vec)))

(defmulti model-commands
  (fn [queue _]
    (first queue)))

(defn model [line]
  (let [line-number (first line)
        queue (vec (rest line))]
    {:line-number line-number
     :commands (model-commands queue [])}))

(defn has-operator [coll operator]
  (< -1 (.indexOf coll operator)))

(defn partition-by-operators [operators coll]
  (->> coll
       (partition-by operators)
       (filter #(or (< 1 (count %)) (not (operators (first %)))))))

(defmulti model-function-call first)

(defmulti model-operand type)

; todo - defmethod model-operand java.lang.Long
; todo - defmethod model-operand clojure.lang.Symbol

(defn model-expression [expression]
  (let [out (reduce
             (fn [out operator]
               (if out out
                   {:expression :operation
                    :operator operator
                    :operands (->> expression
                                   (partition-by-operators #{operator}))}))
             false
             '[+ - * /])]
    (if out out
        (if (= 2 (count expression))
          (model-function-call expression)
          (model-operand (first expression))))))

(defmethod model-function-call 'RND [_]
  {:expression :function
   :function :random})

; todo - model-function-call 'INT
; todo - model-function-call :default

(defn parse-comparison [predicate]
  (let [[left operator right] (partition-by '#{< > = <> <= >=} predicate)]
    {:predicate (first operator)
     :left (model-expression left)
     :right (model-expression right)}))

(defn parse-conjuctions [predicate conjunction]
  {:predicate conjunction
   :comparisons (->> predicate
                     (partition-by-operators #{conjunction})
                     (map parse-comparison))})

(defn model-predicate [predicate]
  (if (has-operator predicate 'AND)
    (parse-conjuctions predicate 'AND)
    (if (has-operator predicate 'OR)
      (parse-conjuctions predicate 'OR)
      (parse-comparison predicate))))

(defn split-queue [queue item]
  [(take-while #(not= item %) queue)
   (drop-while #(not= item %) queue)])

(defmethod model-commands 'IF [queue out]
  (let [[if-block then-queue] (split-queue queue 'THEN)
        if-test (model-predicate if-block)
        [then-block else-block] (split-queue then-queue 'ELSE)
        then (model-commands then-block [])]
    (if (empty? else-block)
      (conj out {:command :if-then
                 :if if-test
                 :then then})
      (conj out {:command :if-then-else
                 :if if-test
                 :then then
                 :else (model-commands else-block [])}))))

(defn step-commands [queue out func]
  (let [[data next-queue] (split-queue (rest queue) :_)]
    (model-commands
     (rest next-queue)
     (conj out (func data)))))

(defmethod model-commands 'ASSIGN [queue out]
  (step-commands queue out
                 #(hash-map
                   :command :assign
                   :variable (first %)
                   :expression (model-expression (drop 2 %)))))

(defmethod model-commands 'CLS [queue out]
  (model-commands (rest queue) (conj out {:command :cls})))

(defmethod model-commands 'DATA [queue out]
  (step-commands queue out #(hash-map :command :data :data %)))

(defmethod model-commands 'DIM [queue out]
  (step-commands queue out #(hash-map :command :read :variable (first %) :dimensions (vec (second %)))))

(defmethod model-commands 'END [queue out]
  (model-commands (rest queue) (conj out {:command :end})))

(defmethod model-commands 'FOR [queue out]
  (step-commands queue out #(hash-map :command :for :variable (first %) :start (nth % 2) :end (nth % 4))))

(defmethod model-commands 'GOSUB [queue out]
  (step-commands queue out #(hash-map :command :gosub :target (first %))))

(defmethod model-commands 'GOTO [queue out]
  (step-commands queue out #(hash-map :command :goto :target (first %))))

(defmethod model-commands 'INPUT [queue out]
  (step-commands queue out #(hash-map :command :input :variable (first %))))

(defmethod model-commands 'NEXT [queue out]
  (step-commands queue out #(hash-map :command :next :variable (first %))))

(defmethod model-commands 'ON [queue out]
  (step-commands queue out #(hash-map :command :on :variable (first %) :targets (drop 2 %))))

(defmethod model-commands 'PRINT [queue out]
  (step-commands queue out #(hash-map :command :print :args (vec %))))

(defmethod model-commands 'READ [queue out]
  (step-commands queue out #(hash-map :command :read :variable (first %) :args (vec (second %)))))

(defmethod model-commands 'REM [queue out]
  (step-commands queue out #(hash-map :command :rem :comment (first %))))

(defmethod model-commands 'RETURN [queue out]
  (model-commands (rest queue) (conj out {:command :return})))

(defmethod model-commands :_ [queue out]
  (model-commands (rest queue) out))

(defmethod model-commands nil [_ out] out)