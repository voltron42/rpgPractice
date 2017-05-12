(ns clojurerpg.common-test
  (:require [clojure.test :refer :all]
            [clojurerpg.common :refer :all]))

(deftest test_expressionDecoder
  (let [lazy-compare #(fn [& args]
                        (loop [params args]
                          (if (empty? params)
                            (not %)
                            (if (= % ((first params)))
                              %
                              (recur (rest params))))))
        opmap (merge (build-opmap '(+ - * /))
                     {'or (lazy-compare true) 'and (lazy-compare false)}
                     {'if #(if (%1) (%2) (%3))})
        resolver (expressionDecoder opmap)]
    (is (= 2 ((resolver '(/ a b))
               '{b 8 a 16})))
    (is (= 8 ((resolver '(+ a b 2))
               '{a 2 b 4})))
    (is (= [6 0 -4]
           (map (resolver '(* (+ 2 a) (- 10 b)))
                '[{a 1 b 8} {b 5 a -2} {a 2 b 11}])))
    (is (= 1 ((resolver '(/ (+ x 2) (* 3 (+ y 1))))
               '{x 4 y 1})))
    (is (= ["yes" "no"]
           (map (resolver '(if a b c))
                '[{a true b "yes" c "no"}
                  {a false b "yes" c "no"}])))
    (is (= [true true true false]
           (map (resolver '(or a b c))
                '[{a true b true c true}
                  {a true b true c false}
                  {a true b false c false}
                  {a false b false c false}])))
    (is (= [true false false false]
           (map (resolver '(and a b c))
                '[{a true b true c true}
                  {a true b true c false}
                  {a true b false c false}
                  {a false b false c false}])))
    ))
