(ns hexmapper.mapping-test
  (:require [hexmapper.mapping :refer :all]
            [clojure.test :refer :all]))

(def sample-mapping {:orientation :hexvert
                     :tiles [{:name :plains :color "green"}
                             {:name :desert :color "yellow"}
                             {:name :mountain :color "grey"}
                             {:name :blank :color "white"}
                             {:name :freshwater :color "lightblue"}
                             {:name :ocean :color "navyblue"}
                             {:name :pit :color "brown"}]
                     :map [[0 1 2] [4 5 6]]
                     :overlay []})

(def sample-config [{:name :hexvert
                     :points [[0 10] [5 1.34] [15 1.34] [20 10] [15 18.66] [5 18.66]]
                     :init [[0] [-1.33 7.33]]
                     :offset [-5 0]}
                    {:name :square
                     :points [[0 0] [0 20] [20 20] [20 0]]
                     :init [[0] [0]]
                     :offset [0 0]
                     }])

(deftest build-mapping-test
  (testing "testing building map"
    (is (= (build-mapping (:tiles sample-mapping) (:map sample-mapping) (first sample-config))
           [
            [:use {:x 0 :y -1.33 :xlink:href "#hexvert" :fill "url(#plains)"}]
            [:use {:x 15 :y 7.33 :xlink:href "#hexvert" :fill "url(#desert)"}]
            [:use {:x 30 :y -1.33 :xlink:href "#hexvert" :fill "url(#mountain)"}]
            [:use {:x 0 :y 15.99 :xlink:href "#hexvert" :fill "url(#freshwater)"}]
            [:use {:x 15 :y 24.65 :xlink:href "#hexvert" :fill "url(#ocean)"}]
            [:use {:x 30 :y 15.99 :xlink:href "#hexvert" :fill "url(#pit)"}]
            ]))))

(deftest build-patterns-test
  (testing "testing building defs"
    (is (= (build-patterns (:tiles sample-mapping))
           [[:pattern {:id "plains" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "green"}]]
            [:pattern {:id "desert" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "yellow"}]]
            [:pattern {:id "mountain" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "grey"}]]
            [:pattern {:id "blank" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "white"}]]
            [:pattern {:id "freshwater" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "lightblue"}]]
            [:pattern {:id "ocean" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "navyblue"}]]
            [:pattern {:id "pit" :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill "brown"}]]
            ]))))

(deftest build-tile-test
  (testing "testing tiles test"
    (is (= (build-tile (first sample-config))
           [:g {:id "hexvert"}
             [:polygon {:points "0,10 5,1.34 15,1.34 20,10 15,18.66 5,18.66"
                        :fill "inherit" :stroke "black" :stroke-width 1}]]))))

(deftest build-dimentions-test
  (testing "testing build dimentions"
    (let [orientation (:orientation sample-mapping)
          built-mapping (build-mapping (:tiles sample-mapping) (:map sample-mapping) (first sample-config))
          dimensions (build-dimensions built-mapping (first sample-config))]
      (is (= 2 (count dimensions)))
      (is (= 50 (first dimensions)))
      (is (= 43.31 (last dimensions)))
      )))