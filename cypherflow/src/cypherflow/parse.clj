(ns cypherflow.parse
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

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
       (mapv (fn [line]
              (let [replaced (reduce #(str/replace %1 (first %2) (second %2)) line replacements)]
                (read-string (str/trim (str "[" replaced "]"))))))))

(defmulti model-commands
  (fn [queue _]
    (pp/pprint {:defmulti queue})
    (first queue)))

(defn throw-error [error-body]
  (pp/pprint {:error-body error-body})
  (throw (ex-info "" error-body)))

(defn model [line]
  (let [line-number (first line)]
    (pp/pprint {:line-number line-number})
    (try
      {:line-number line-number
       :commands (model-commands (vec (rest line)) [])}
      (catch clojure.lang.ExceptionInfo e
        (throw-error (assoc (ex-data e) :line-number line-number))))))

(defn has-operator [coll operator]
  (< -1 (.indexOf coll operator)))

(defn partition-by-operators [operators coll]
  (->> coll
       (partition-by operators)
       (filter #(or (< 1 (count %)) (not (operators (first %)))))))

(defmulti model-function-call first)

(defn validate-operand [operand]
  (if (#{java.lang.Long java.lang.Double clojure.lang.Symbol java.lang.String} (type operand))
    operand 
    (throw-error {:operand operand})))

(defn model-expression [expression]
  (let [out (reduce
             (fn [out operator]
               (if out out
                   (when (has-operator expression operator)
                     {:expression :operation
                      :operator operator
                      :operands (->> (partition-by-operators #{operator} expression)
                                     (mapv model-expression))})))
             false
             '[+ - * /])]
    (if out out
        (if (= 2 (count expression))
          (model-function-call expression)
          (if (= 1 (count expression))
            (validate-operand (first expression))
            (throw-error {:expression expression}))))))

(defmethod model-function-call 'RND [[_ arg-list]]
  {:expression :function
   :function :random
   :args (model-expression arg-list)})

(defmethod model-function-call 'INT [[_ arg-list]]
  {:expression :function
   :function :INT
   :args (model-expression arg-list)})

(defmethod model-function-call 'LEFT$ [[_ [string-var character-count]]]
  {:expression :function
   :function :left-string
   :string-var string-var
   :character-count character-count})

(defmethod model-function-call :default [[array-var coordinates]]
  (pp/pprint {:model-function-call :default :array-var array-var :coordinates coordinates})
  (let [{operands :operands 
         errors :errors} (reduce
                           (fn [{operands :operands errors :errors} coordinate]
                             (try
                               (validate-operand coordinate)
                               {:operands (conj operands coordinate) :errors errors}
                               (catch clojure.lang.ExceptionInfo _
                                 {:operands operands :errors (conj coordinate errors)})))
                           {:operands [] :errors []}
                           coordinates)]
    (if (empty? errors)
      {:expression :array-ref
       :array-var array-var
       :coordinates operands}
      (throw-error {:bad-coordinates errors}))))

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
  (pp/pprint {:model-commands 'IF})
  (let [[if-block then-queue] (split-queue queue 'THEN)
        [then-block else-block] (split-queue then-queue 'ELSE)
        if-test (model-predicate (rest if-block))
        then (model-commands (rest then-block) [])
        output (if (empty? else-block)
                 {:command :if-then
                  :if if-test
                  :then then}
                 {:command :if-then-else
                  :if if-test
                  :then then
                  :else (model-commands (rest else-block) [])})]
    (pp/pprint 
     {:if-block if-block
      :then-queue then-queue
      :then-block then-block
      :else-block else-block
      :output output})
    (conj out output)))

(defn step-commands [queue out func]
  (let [[data next-queue] (split-queue (rest queue) :_)]
    (model-commands
     (rest next-queue)
     (let [item (func data)]
       (if (some? item) (conj out item) out)))))

(defmethod model-commands 'CLS [queue out]
  (pp/pprint {:model-commands 'CLS})
  (model-commands (rest queue) (conj out {:command :cls})))

(defmethod model-commands 'DATA [queue out]
  (pp/pprint {:model-commands 'DATA})
  (step-commands queue out #(hash-map :command :data :data %)))

(defmethod model-commands 'DIM [queue out]
  (pp/pprint {:model-commands 'DIM})
  (step-commands queue out #(hash-map :command :dim :array-ref (model-function-call %))))

(defmethod model-commands 'END [queue out]
  (pp/pprint {:model-commands 'END})
  (model-commands (rest queue) (conj out {:command :end})))

(defmethod model-commands 'FOR [queue out]
  (pp/pprint {:model-commands 'FOR})
  (step-commands queue out #(hash-map :command :for :variable (first %) :start (nth % 2) :end (nth % 4))))

(defmethod model-commands 'GOSUB [queue out]
  (pp/pprint {:model-commands 'GOSUB})
  (step-commands queue out #(hash-map :command :gosub :target (first %))))

(defmethod model-commands 'GOTO [queue out]
  (pp/pprint {:model-commands 'GOTO})
  (step-commands queue out #(hash-map :command :goto :target (first %))))

(defmethod model-commands 'INPUT [queue out]
  (pp/pprint {:model-commands 'INPUT})
  (step-commands queue out (fn [[prompt var]]
                             (let [output (if (some? var)
                                            {:prompt prompt :variable var}
                                            {:variable prompt})]
                               (assoc output :command :input)))))

(defmethod model-commands 'NEXT [queue out]
  (pp/pprint {:model-commands 'NEXT})
  (step-commands queue out #(hash-map :command :next :variable (first %))))

(defmethod model-commands 'ON [queue out]
  (pp/pprint {:model-commands 'ON})
  (step-commands queue out #(hash-map :command :on :variable (first %) :targets (vec (drop 2 %)))))

(defmethod model-commands 'PRINT [queue out]
  (pp/pprint {:model-commands 'PRINT})
  (step-commands queue out #(hash-map :command :print :args (vec %))))

(defmethod model-commands 'READ [queue out]
  (pp/pprint {:model-commands 'READ})
  (step-commands queue out #(hash-map :command :read :array-ref (model-function-call %))))

(defmethod model-commands 'REM [queue out]
  (step-commands queue out (constantly nil)))

(defmethod model-commands 'RETURN [queue out]
  (pp/pprint {:model-commands 'RETURN})
  (model-commands (rest queue) (conj out {:command :return})))

(defmethod model-commands :_ [queue out]
  (pp/pprint {:model-commands :_})
  (model-commands (rest queue) out))

(defmethod model-commands nil [_ out]
  (pp/pprint {:model-commands nil})
  out)

(defmethod model-commands :default [queue out]
  (pp/pprint {:model-commands :default})
  (if (int? (first queue))
    (do
      (pp/pprint {:model-commands [:default 'GOTO]})
      (model-commands (cons 'GOTO queue) out))
    (if (and (symbol? (first queue)) (= '= (second queue)))
      (do
        (pp/pprint {:model-commands [:default :assign]})
        (step-commands queue out
                       #(hash-map
                         :command :assign
                         :variable (first queue)
                         :expression (model-expression (rest %)))))
      (if (and 
           (< 3 (count queue))
           (symbol? (first queue))
           (list? (second queue))
           (= '= (nth queue 2)))
      (do
        (pp/pprint {:model-commands [:default :assign-to-array-ref]})
        (step-commands queue out
                       #(hash-map
                         :command :assign-to-array-ref
                         :array-ref (model-function-call (take 2 queue))
                         :expression (model-expression (drop 2 %))))) 
        (throw-error {:queue queue :out out})))))
