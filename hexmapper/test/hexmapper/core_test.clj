(ns hexmapper.core-test
  (:require [clojure.test :refer :all]
            [hexmapper.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest cml-mapping-test
  (testing "Lets see if this works..."
      (is (= (map-clm [:html
                       [:body
                        [:h2 "Hello World!"]
                        :hr
                        [:p "The quick brown fox jumped over the lazy dog!"]
                        ]
                       ])
             {:tag :html
              :content [{:tag :body
                         :content [{:tag :h2
                                    :content ["Hello World!"]}
                                   {:tag :hr}
                                   {:tag :p
                                    :content ["The quick brown fox jumped over the lazy dog!"]}
                                   ]}]}))
    )
  )
