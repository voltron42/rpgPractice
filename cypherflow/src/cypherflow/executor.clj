(ns cypherflow.executor)

(defmulti execute-command 
  (fn [command _] 
    (:command command)))

(defn run [program]
  (let [data (->> program
                  (filter #(= :data (-> % :commands first :command)))
                  (sort-by :line-number)
                  (reduce #(into %1 (-> %2 :commands first :data)) []))
        line-numbers (mapv :line-number program)
        line-index (into {} (mapv vector line-numbers (range)))
        step #(find line-numbers (inc (get line-index %)))
        context (atom {:step step
                       :next (first line-numbers)
                       :return []
                       :data data
                       :vars {}})]
    (while (not (:end @context))
      (let [next-line-number (:next @context)
            next-line-index (find line-index next-line-number)
            next-line (nth program next-line-index)
            current-context (-> @context (assoc :current next-line-number) (dissoc :next))
            result (reduce
                    #(execute-command %2 %1)
                    current-context
                    (:commands next-line))
            result (if (or (:end result) (:next result))
                     result
                     (let [new-line-number (find line-numbers (inc next-line-index))]
                       (if (some? new-line-number)
                         (assoc result :next new-line-number)
                         (assoc result :end true))))]
        (reset! context result)))))

(defmulti resolve-expression 
  (fn [expression _] 
    (:expression expression)))

(defmulti resolve-predicate 
  (fn [predicate _] 
    (:predicate predicate)))

(defmethod execute-command :assign 
  [{variable :variable expression :expression} context]
  (update context :vars assoc variable (resolve-expression expression context)))

(defmethod execute-command :assign-to-array-ref 
  [{{array-var :array-var coordinates :coordinates} :array-ref expression :expression} context]
  ; todo
  )

(defmethod execute-command :cls [_ context]
  ; todo
  )

(defmethod execute-command :dim 
  [{{array-var :array-var coordinates :coordinates} :array-ref} context]
  ; todo
  )

(defmethod execute-command :end [_ context]
  (assoc context :end true))

(defmethod execute-command :for 
  [{variable :variable start :start end :end} context]
  ; todo
  )

(defmethod execute-command :gosub [{target :target} context]
  ;todo
  )

(defmethod execute-command :goto [{target :target} context]
  (assoc context :next target))

(defmethod execute-command :if-then [{if-block :if then-block :then} context]
  (let [predicate (resolve-predicate if-block context)
        commands (if predicate then-block [])]
    (reduce
     #(execute-command %2 %1)
     context
     commands)))

(defmethod execute-command :if-then-else [{if-block :if then-block :then else-block :else} context]
  (let [predicate (resolve-predicate if-block context)
        commands (if predicate then-block else-block)]
    (reduce
     #(execute-command %2 %1)
     context
     commands)))

(defmethod execute-command :input [{prompt :prompt variable :variable} context]
  (when (some? prompt)
    (println prompt))
  (update context :vars assoc variable (read-line)))

(defmethod execute-command :next [{variable :variable} context]
  ;todo
  )

(defmethod execute-command :on [{targets :targets variable :variable} context]
  (if-let [next (find targets (find (:vars context) variable))]
    (execute-command {:command :gosub :target next} context)
    (assoc context :end true)))

(defmethod execute-command :print [{args :args} context]
  (let [args (mapv #(if (symbol? %) (get (:vars context) %) %) args)]
    (apply println args))
  context)

(defmethod execute-command :read 
  [{{array-var :array-var coordinates :coordinates} :array-ref} context]
  ; todo
  )

(defmethod execute-command :return [_ context]
  ; todo
  )

(defmulti resolve-function (fn [expression _] (:function expression)))

(defmethod resolve-expression :function [expression context]
  (resolve-function expression context))

(defmethod resolve-expression :operation [{operator :operator operands :operands} context] 
  (reduce 
   (resolve operator)
   (resolve-expression (first operands) context)
   (mapv #(resolve-expression % context) (rest operands))))

(defmethod resolve-expression :array-ref [{array-var :array-var coordinates :coordinates} context]
  ; todo
  )

(defmethod resolve-expression :default [expression context]
  (if (symbol? expression)
    (get-in context [:vars expression])
    (if (or (number? expression) (string? expression))
      expression
      (throw (ex-info "" { :resolve-expression expression })))))

(def conjunctions
  {:and false
   :or true})

(defmethod resolve-predicate :conjunction 
  [{comparisons :comparisons conjunction :conjunction} context]
  (let [state (get conjunctions conjunction)]
    (loop [predicates comparisons]
      (if (empty? predicates)
        (not state)
        (if (= state (resolve-predicate (first predicates) context))
          state
          (recur (rest predicates)))))))

(defmethod resolve-predicate :comparison 
  [{operator :operator left :left right :right} context]
  ((resolve operator) 
   (resolve-expression left context) 
   (resolve-expression right context)))