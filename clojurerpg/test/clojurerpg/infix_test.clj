(ns clojurerpg.infix-test
  (:require [clojure.test :refer :all]
            [clojurerpg.infix :refer :all]))

(deftest test-reorder
  (testing "testing reorder"
    (is (= (reorder '(2 + 3)) '(+ 2 3)))
    (is (= (reorder '(4 * 3)) '(* 4 3)))
    (is (= (reorder '(2 + 3 + 4)) '(+ 2 3 4)))
    (is (= (reorder '(2 + 3 - 4)) '(- (+ 2 3) 4)))
    (is (= (reorder '(2 + 3 - 4 + 5)) '(+ (- (+ 2 3) 4) 5)))
    (is (= (reorder '((2 + 3) - (4 + 5))) '(- (+ 2 3) (+ 4 5))))
    (is (= (reorder '(2 + 3 * 4)) '(+ 2 (* 3 4))))
    (is (= (reorder '(1 + 2 + 3 * 4)) '(+ 1 2 (* 3 4))))
    (is (= (reorder '(1 + 2 * 3 + 4)) '(+ 1 (* 2 3) 4)))
    (is (= (reorder '((1 + 2) * (3 + 4))) '(* (+ 1 2) (+ 3 4))))
    ))