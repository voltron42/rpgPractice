(ns cypherflow.parse-test
  (:require [clojure.test :as t]
            [cypherflow.parse :as cfp]))

(t/deftest test-parse-rem
  (t/testing "testing parse REM"
    (cfp/parse ["10 REM WEREWOLVES AND WANDERER"])))