(ns clojurerpg.flow
  (:require [clojure.edn :as edn]
            [clojurerpg.common :as common]
            [clojure.set :as set]
            [clojurerpg.lang.edn :refer :all]
            [clojure.spec.alpha :as s])
  (:import (javafx.animation PauseTransition)
           (clojurerpg.control Halt Continue Break)
           (clojure.lang PersistentArrayMap))
  (:gen-class))

(defn- get-type [elem & _]
  (cond
    (vector? elem) :block
    (list? elem) :expression
    (map? elem) :declare
    (string? elem) :print-text
    (keyword? elem) :variable
    (symbol? elem) :command
    :else :unknown))

(defn- d [sideCount]
  (fn rollDice
    ([] (rollDice 1))
    ([numberOf]
     (reduce + 0 (map #(inc (rand-int %))
                      (repeat numberOf sideCount))))))

(def ^:private dice
  {'d4    (d 4)
   'd6    (d 6)
   'd8    (d 8)
   'd10   (d 10)
   'd12   (d 12)
   'd20   (d 20)
   'd100  (d 100)})

(def ^:private copy-ops '#{+ - * / > < >= <= = peek pop empty? count keys vars nil?})

(def ^:private transpose
  {'in    contains?
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
   'rand  rand-int
   '?     read-line})

(def ^:private custom
  {'int   #(try
             (Integer/parseInt %1)
             (catch NumberFormatException nfe %2))
   'set   #(set (vec %&))
   '?=    #(if % true false)
   '!?=   #(if % false true)
   '$#!+  #(throw (Exception. (str %)))
   '<-    (fn [what in where] (concat (take where in) (vector what) (drop (inc where) in)))})

(defn- lazy-compare [state]
  (fn [& args]
    (loop [params args]
      (if (empty? params)
        (not state)
        (if (= state ((first params)))
          state
          (recur (rest params)))))))

(def decodeExpression
  (let [opmap (merge (common/build-opmap copy-ops)
                     (common/build-opmap (merge transpose dice custom))
                     {'or (lazy-compare true)
                      'and (lazy-compare false)
                      'if #(if (%1) (%2) (%3))})]
    (common/expressionDecoder opmap)))

(defmulti resolve-expression get-type)

(defmulti resolve-action get-type)

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
    ))

(defmethod resolve-expression :declare [expression lines state commands recursor]
  (let [{:keys [init while step] :or {init {} while true step {}}} expression
        blackout (set/difference (set (keys init)) (set (keys state)))
        update #((typemap %1 recursor) %2 %3)
        retstate #(reduce (fn [a b] (dissoc a b)) % blackout)]
    (loop [loopstate (:state (update init state commands))]
      (if (not ((decodeExpression while) loopstate))
        (retstate loopstate)
        (let [{:keys [nextstate breakstate continuestate]} (try {:nextstate (recursor lines loopstate commands)}
                                                                (catch Break b {:breakstate (.getState b)})
                                                                (catch Continue c {:continuestate (.getState c)}))]
          (if (some? breakstate)
            (retstate breakstate)
            (let [stepstate (:state (update step (if (some? continuestate) continuestate nextstate) commands))]
              (recur stepstate))))))))

(defmethod resolve-expression :expression [expression lines state commands recursor]
  (if ((decodeExpression expression) state)
    (recursor lines state commands)
    state))

(defmethod resolve-action :block [[expression & sublines] recursor]
  (fn [{:keys [state commands] :as ctx}]
    (assoc ctx :state (resolve-expression expression sublines state commands recursor))))

(defmethod resolve-action :declare [line recursor]
  (fn [{:keys [state commands]}]
    (let [[varupdate commandupdate] (map #(into {} (filter (fn [[k v]] (% k)) line)) [keyword? symbol?])]
      {:state (reduce-kv (fn [out variable expression]
                        (if (nil? expression)
                          (dissoc out variable)
                          (assoc out variable ((decodeExpression expression) state))))
                      state varupdate)
       :commands (reduce-kv (fn [out cmd block]
                           (if (vector? block)
                             (assoc out cmd (fn [{:keys [state commands]}]
                                              (recursor block state commands)))
                             (throw (Exception. (str "Not a valid procedure: " cmd)))))
                         commands commandupdate)})))

(defmethod resolve-action :command [line _]
  (fn [{:keys [state commands] :as ctx}]
    (let [cmd (get commands line (fn [_ _] (throw (Exception. (str "\"" line "\" is not a valid command")))))]
      (assoc ctx :state (cmd ctx)))))

(defmethod resolve-action :expression [line _]
  (fn [{:keys [state] :as ctx}]
    (println (str line " = " ((decodeExpression line) state)))
    ctx))

(defmethod resolve-action :variable [line _]
  (fn [{:keys [state] :as ctx}]
    (println (get state line))
    ctx))

(defmethod resolve-action :default [line _]
  (fn [ctx]
    (println line)
    ctx))

(defn- runflow-recursive [flow flowstate flowcommands]
  (get
    (reduce
      (fn [context line]
        ((resolve-action line runflow-recursive) context))
      {:state flowstate
       :commands flowcommands}
      flow)
    :state))

(defn runflow [flow flowstate]
  (try
    (runflow-recursive
      flow
      flowstate
      {'...      #(do (Thread/sleep 1000) %)
       'break    #(throw (Break. ^PersistentArrayMap (:state %1)))
       'continue #(throw (Continue. ^PersistentArrayMap (:state %1)))
       'halt     (fn [_] (throw (Halt.)))})
    (catch Halt h
      (println "PROGRAM HALTED"))))

(defn -main [& args]
  (when (empty? args) (throw "must pass in a path to the program file"))
  (let [code (edn/read-string (slurp (first args)))
        {:keys [problems]} (s/explain-data :clojurerpg.lang.edn/program code)]
    (if (empty? problems)
      (try
        (runflow code {})
        (catch Throwable e
          (println e)))
      (println (pr-str (count problems))))
    "COMPLETE"))
