(ns clojurerpg.core
  (require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clojurerpg.common :as common]
    )
  )

(def decodeExpression (
                        (fn []
                          (let
                            [
                             d (fn makeDice [sideCount]
                                 (fn rollDice
                                   ([] (rollDice 1))
                                   ([numberOf] (reduce + 0 (map #(inc (rand-int %)) (repeat numberOf sideCount))))
                                   )
                                 )
                             opmap {
                                    '+ +
                                    '- -
                                    '* *
                                    '/ /
                                    '> >
                                    'd4 (d 4)
                                    'd6 (d 6)
                                    'd8 (d 8)
                                    'd10 (d 10)
                                    'd12 (d 12)
                                    'd20 (d 20)
                                    'd100 (d 100)
                                    }
                             ]
                            (common/expressionDecoder opmap)
                            )
                          )
                        )
  )

(defn printLines [allLines state]
  (loop [lines allLines]
    (let [line (first lines)
          remaining (rest lines)
          ]
      (if (coll? line)
        (let [
              expression (first line)
              sublines (rest line)
              ]
          (when
            ((decodeExpression expression) state)
            (printLines sublines state)
            )
          )
        (println line)
        )
      (when-not
        (empty? remaining)
        (recur remaining)
        )
      )
    )
  )

(defn move [data rooms directions state current]
  (loop [current (:start data)]
    (let
      [
       room (rooms current)
       doors (:doors room)
       description (:description room)
       ]
      (printLines description state)
      (println "WHERE WOULD YOU LIKE TO GO?")
      (printLines (map name (keys doors)) state)
      (Thread/sleep 1000)
      (let
        [
         input (keyword (clojure.string/upper-case (read-line)))
         error (if
                (= input :QUIT)
                (throw (Exception. "QUITING GAME"))
                (if
                  (contains? directions input)
                  (when-not (contains? doors input)
                    (str "THERE IS NO DOOR TO THE " input)
                    )
                  (str input " IS NOT A VALID DIRECTION")
                  )
                )
         ]
        (if error
          (do
            (println error)
            (recur current)
            )
          (doors input)
          )
        )
      )
    )
  )

(defn controlLoop [data gamestate]
  (try
    (let [
          rooms (:rooms data)
          directions (:directions data)
          ]
      (loop [state gamestate]                                   ; main control loop
        (let [
              stepstate (merge state {:STRENGTH (- (:STRENGTH state) 5) :TALLY (inc (:TALLY state))})
              ]

          (recur (assoc stepstate :ROOM (move data rooms directions stepstate (:ROOM stepstate))))
          )
        )
      )
    (catch Exception e (println (.getMessage e)))
    )
  )

(defn -main [& args]
  (when (empty? args) (throw "must pass in a path to the game file"))
  (let
    [
     filename (first args)
     data (edn/read-string (slurp filename))
     state (:init data)
     ]
    (println "TYPE 'QUIT' TO QUIT THE GAME")
    (controlLoop data state)
    )
  )
