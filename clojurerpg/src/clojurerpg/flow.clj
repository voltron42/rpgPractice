(ns clojurerpg.flow
  (:require [clojure.edn :as edn]
            [clojurerpg.common :as common]
            [clojure.set :as set])
  (:import (javafx.animation PauseTransition)
           (clojurerpg Break Continue Halt)))

(def decodeExpression ((fn []
                          (let
                            [lazy-compare #(fn [& args]
                                             (loop [params args]
                                               (if (empty? params)
                                                 (not %)
                                                 (if (= % ((first params)))
                                                   %
                                                   (recur (rest params))))))
                             d (fn makeDice [sideCount]
                                 (fn rollDice
                                   ([] (rollDice 1))
                                   ([numberOf] (reduce + 0 (map #(inc (rand-int %)) (repeat numberOf sideCount))))))
                             opmap (merge (common/build-opmap '(+ - * / > < >= <= = peek pop empty? count keys vars nil?))
                                          (common/build-opmap {'in    contains?
                                                               'int   #(try
                                                                         (Integer/parseInt %1)
                                                                          (catch NumberFormatException nfe %2))
                                                               '__    int
                                                               '++    inc
                                                               '<>    not=
                                                               '$     str
                                                               '!     not
                                                               '!nil? some?
                                                               '<<    assoc
                                                               '>>    get
                                                               '->    nth
                                                               'list  vector
                                                               'push  conj
                                                               'map   hash-map
                                                               'set   #(set (vec %&))
                                                               'rand  rand-int
                                                               'd4    (d 4)
                                                               'd6    (d 6)
                                                               'd8    (d 8)
                                                               'd10   (d 10)
                                                               'd12   (d 12)
                                                               'd20   (d 20)
                                                               'd100  (d 100)
                                                               '?     read-line
                                                               '?=    #(if % true false)
                                                               '!?=   #(if % false true)
                                                               '$#!+  #(throw (Exception. (str %)))
                                                               '<-    (fn [what in where] (concat (take where in) (vector what) (drop (inc where) in)))
                                                               })
                                          {'or (lazy-compare true)
                                           'and (lazy-compare false)
                                           'if #(if (%1) (%2) (%3))})]
                            (common/expressionDecoder opmap)))))

(defn typemap [singleline recursor]
  (cond
    (vector? singleline) (fn [state commands]
                           (let [expression (first singleline)
                                 sublines (rest singleline)
                                 endstate (cond
                                                 (list? expression) (if ((decodeExpression expression) state) (recursor sublines state commands) state)
                                                 (map? expression) (let [{:keys [init while step]} expression
                                                                         init (if (nil? init) {} (if (map? init) init (throw (Exception. (str "Not a valid initial state: " init)))))
                                                                         while (if (nil? while) true (if (list? while) while (throw (Exception. (str "Not a valid while condition: " while)))))
                                                                         step (if (nil? step) {} (if (map? step) step (throw (Exception. (str "Not a valid state iteration: " step)))))
                                                                         blackout (set/difference (set (keys init)) (set (keys state)))
                                                                         update #((typemap %1 recursor) %2 %3)
                                                                         retstate #(reduce (fn [a b] (dissoc a b)) % blackout)]
                                                                     (loop [loopstate (:state (update init state commands))]
                                                                       (if (not ((decodeExpression while) loopstate))
                                                                         (retstate loopstate)
                                                                         (let [{:keys [nextstate breakstate continuestate]} (try {:nextstate (recursor sublines loopstate commands)}
                                                                                                                                 (catch Break b {:breakstate (.getState b)})
                                                                                                                                 (catch Continue c {:continuestate (.getState c)}))]
                                                                           (if (some? breakstate)
                                                                             (retstate breakstate)
                                                                             (let [stepstate (:state (update step (if (some? continuestate) continuestate nextstate) commands))]
                                                                               (recur stepstate))))))))]
                           {:state endstate :commands commands}))
    (list? singleline) (fn [state commands] (do (println (str singleline " = " ((decodeExpression singleline) state))) {:state state :commands commands}))
    (map? singleline) (fn [state commands]
                        (let [[varupdate commandupdate] (map #(into {} (filter (fn [[k v]] (% k)) singleline)) [keyword? symbol?])]
                          (let [newstate (reduce (fn [out [k v]]
                                                   (if (nil? v)
                                                     (dissoc out k)
                                                     (assoc out k ((decodeExpression v) state))))
                                                 state varupdate)
                                newcommands (reduce (fn [out [k v]]
                                                      (if (vector? v)
                                                        (assoc out k #(recursor v %1 %2))
                                                        (throw (Exception. (str "Not a valid procedure: " k)))))
                                                    commands commandupdate)]
                            {:state newstate :commands newcommands})))
    (keyword? singleline) (fn [state commands] (do (println (state singleline)) {:state state :commands commands}))
    (symbol? singleline) (fn [state commands]
                           (if (contains? commands singleline)
                             {:state ((commands singleline) state commands) :commands commands}
                             (throw (Exception. (str singleline " is not a valid command")))))))

(defn- runflow-recursive [flow flowstate flowcommands]
  (loop [lines flow state flowstate commands flowcommands]
    (let [line (first lines)
          remaining (rest lines)
          typeaction (typemap line runflow-recursive)
          typefunc (if (nil? typeaction) (fn [s c] (println line) {:state s :commands c}) typeaction)
          newstate (typefunc state commands)]
        (if (empty? remaining)
          (:state newstate)
          (recur remaining (:state newstate) (:commands newstate))))))

(defn runflow [flow flowstate]
  (try
    (runflow-recursive
      flow
      flowstate
      {'...      (fn [state commands] (Thread/sleep 1000) state)
       'break    #(throw (Break. %1 %2))
       'continue #(throw (Continue. %1 %2))
       'halt     (fn [_ _] (throw (Halt.)))})
    (catch Halt h
      (println "PROGRAM HALTED"))))

(defn -main [& args]
  (when (empty? args) (throw "must pass in a path to the program file"))
  (let [_ (try
            (runflow (edn/read-string (slurp (first args))) {})
            (catch Throwable e
              (println e)))]
    "COMPLETE"))
