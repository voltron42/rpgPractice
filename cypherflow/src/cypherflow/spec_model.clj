(ns cypherflow.spec-model
  (:require [clojure.spec.alpha :as s]))

(s/def ::code (s/and vector?
                     (s/cat :lines (s/+ ::line))))
(s/def ::line (s/and vector?
                     (s/cat :line-number int?
                            (s/or :command-seq ::command-seq
                                  :if-block ::if-block))))

(s/def ::if-block (s/cat :if-label '#{IF}
                         :predicate ::predicate
                         :then-label '#{THEN}
                         :then-block ::command-seq
                         :else (s/? (s/cat :else-label '#{ELSE} 
                                           :else-block ::command-seq))))

; todo ::predicate

(s/def ::command-seq (s/cat :command ::command
                            :next (s/* (s/cat :delim #{:_}
                                              :next-command ::command))))

(s/def ::command (s/or :command-word '#{CLS END RETURN}
                       :assign ::assign ;!
                       :data ::data ;!
                       :dim ::dim ;!
                       :for ::for ;!
                       :gosub ::gosub ;!
                       :goto ::goto ;!
                       :input ::input
                       :next ::next
                       :on ::on
                       :print ::print ;!
                       :read ::read ;!
                       :rem ::rem)) ;!

(s/def ::assign (s/cat :variable ::variable
                       :equals '#{=}
                       :expression ::expression))

(s/def ::data (s/cat :label '#{DATA}
                     :data (s/+ int?)))

(s/def ::dim (s/cat :label '#{DIM}
                    :variable ::variable
                    :dimensions (s/and list? (s/every int?))))

(s/def ::for (s/cat :label '#{FOR}
                    :variable ::variable
                    :start int?
                    :to-label '#{TO}
                    :end int?))

(s/def ::gosub (s/cat :label '#{GOSUB}
                      :location int?))

(s/def ::goto (s/cat :label '#{GOTO}
                      :location int?))

(s/def ::input (s/cat :label '#{INPUT}
                      :variable ::variable))

(s/def ::on (s/cat :label '#{ON}
                   :variable ::variable
                   :gosub-label '#{GOSUB}
                   :destinations (s/+ int?)))

(s/def ::print (s/cat :label '#{PRINT}
                      :args (s/* (s/or :text string?
                                       :expression ::expression))))

(s/def ::read (s/cat :label '#{READ}
                     :variable ::variable
                     :coordinates (s/and list? (s/every (s/or :int int?
                                                              :variable ::variable)))))

(s/def ::rem (s/cat :label '#{REM}
                    :comment string?))

; todo ::variable
; todo ::expression