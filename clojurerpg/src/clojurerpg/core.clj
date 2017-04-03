(ns clojurerpg.core
  (require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    )
  )

(defn printLines [allLines]
  (loop [lines allLines]
    (let [line (first lines)
          remaining (rest lines)
          ]
      (if (col? line)
        (let [
              expression (first line)
              ]
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

(defn controlLoop [data]
  (try
    (let [
          rooms (:rooms data)
          directions (:directions data)
          ]
      (loop [current (:start data)]
        (let
          [
           room (rooms current)
           doors (:doors room)
           description (:description room)
           ]
          (printLines description)
          (println "WHERE WOULD YOU LIKE TO GO?")
          (printLines (map name (keys doors)))
          (Thread/sleep 1000)
          (let
            [
             input (keyword (clojure.string/upper-case (read-line)))
             step (if
                    (= input :EXIT)
                    (throw (Exception. "EXITING GAME"))
                    (if
                      (contains? directions input)
                      (if
                        (contains? doors input)
                        {:room (doors input)}
                        {:error (str "THERE IS NO DOOR TO THE " input) :room current}
                        )
                      {:error (str input " IS NOT A VALID DIRECTION") :room current}
                      )
                    )
             ]
            (when
              (:error step)
              (println (:error step))
              )
            (recur (:room step))
            )
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
     ]
    (println "TYPE 'EXIT' TO QUIT")
    (controlLoop data)
    )
  )
