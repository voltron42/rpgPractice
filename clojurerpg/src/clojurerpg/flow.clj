(ns clojurerpg.flow
  (:require [clojure.edn :as edn]
            [clojurerpg.common :as common])
  (:import (javafx.animation PauseTransition)))

(def decodeExpression ((fn []
                          (let
                            [d (fn makeDice [sideCount]
                                 (fn rollDice
                                   ([] (rollDice 1))
                                   ([numberOf] (reduce + 0 (map #(inc (rand-int %)) (repeat numberOf sideCount))))))
                             opmap {'+ +
                                    '- -
                                    '* *
                                    '/ /
                                    '> >
                                    '< <
                                    '<= <=
                                    '>= >=
                                    '= =
                                    '!= not=
                                    '$ str
                                    '! not
                                    'd4 (d 4)
                                    'd6 (d 6)
                                    'd8 (d 8)
                                    'd10 (d 10)
                                    'd12 (d 12)
                                    'd20 (d 20)
                                    'd100 (d 100)
                                    '? #(read-line)
                                    '?= #(if % true false)
                                    '!?= #(if % false true)
                                    '$#!+ #(throw (Exception. (str %)))
                                    '| (fn [firstArg & args] (reduce #(or %1 %2) firstArg args))
                                    '& (fn [firstArg & args] (reduce #(and %1 %2) firstArg args))}]
                            (common/expressionDecoder opmap)))))

(defn typemap [recursor]
  (let [commands {'... (fn [line state]
                           (do
                             (Thread/sleep 1000)
                             state))}]
    {vector? (fn [line state]
               (let [expression (first line)
                     sublines (rest line)]
                 (if ((decodeExpression expression) state)
                   (recursor sublines state)
                   state)))
     list? (fn [line state]
             (do
               (println (str line " = " ((decodeExpression line) state)))
               state))
     map? (fn [line state]
            (reduce (fn [out [k v]]
                      (if (nil? v)
                        (dissoc out k)
                        (assoc out k ((decodeExpression v) state))))
                    state line))
     keyword? (fn [line state]
                (do
                  (println (state line))
                  state))
     symbol? (fn [line state]
               (if (contains? commands line)
                 ((commands line) line state)
                 (throw (str line " is not a valid command"))))}))

(defn runflow [flow flowstate]
  (try
    (loop [lines flow state flowstate]
      (let [line (first lines)
            remaining (rest lines)
            typeaction (first (map second (filter (fn [[k v]] (k line)) (typemap runflow))))
            typefunc (if (nil? typeaction) (fn [l s] (do (println l)) s) typeaction)
            newstate (typefunc line state)]
        (if (empty? remaining)
          newstate
          (recur remaining newstate))))
    (catch Throwable t
      (println (.getMessage t)))))

(defn -main [& args]
  (when (empty? args) (throw "must pass in a path to the program file"))
  (let [_ (runflow (edn/read-string (slurp (first args))) {})]
    "COMPLETE"))