(ns hexmapper.mapping
  (:require [clojure.string :as s]))

(defn apply-xy [func points] (apply mapv (into [(fn [& args] (func args))] points)))

(defn calc-size [points] (apply-xy #(- (apply max %) (apply min %)) points))

(defn build-mapping [tiles mapping shape]
    (let [rowcount (count mapping)
          rowoffsets (take rowcount (cycle (first (:init shape))))
          size (calc-size (:points shape))
          [x-size y-size] (map #(+ (% size) (% (:offset shape))) [first last])
          ]
      (map
        (fn [{:keys [col fill row x-off y-off]}]
          [:use {:x (+ x-off (* col x-size))
                 :y (+ y-off (* row y-size))
                 :xlink:href (str "#" (name (:name shape)))
                 :fill (format "url(#%s)" (name fill))}])
        (loop [results []
             rows mapping]
        (let [rownum (- rowcount (count rows))
              firstrow (first rows)
              coloffsets (take (count firstrow) (cycle (last (:init shape))))
              ]
          (if (empty? rows)
            results
            (recur (into results
                         (loop [row []
                                cells (first rows)]
                           (let [col (count row)]
                             (if (empty? cells)
                               row
                               (recur (conj row {:row rownum
                                                 :col col
                                                 :x-off (nth rowoffsets rownum)
                                                 :y-off (nth coloffsets col)
                                                 :fill (:name (nth tiles (first cells)))
                                                 }) (rest cells)))
                             )
                           ))
                   (rest rows)))
          )
        )
      )
    )
  )

(defn build-patterns [tiles]
  (map #(let [header [:pattern {:id (name (:name %)) :width 20 :height 20} [:rect {:x 0 :y 0 :width 20 :height 20 :fill (:color %)}]]]
          (if (:contents %) (into header (:contents %)) header)) tiles))

(defn build-tile [shape]
  [:g {:id (name (:name shape))}
              [:polygon {:points (s/join " " (map (partial s/join ",") (:points shape)))
                         :fill "inherit" :stroke "black" :stroke-width 1}]])

(defn build-dimensions [built-mapping shape]
  (let [maxXY (apply-xy #(apply max %) (:points shape))
        maxLoc (apply-xy #(apply max %) (map #(let [item (second %)] [(:x item) (:y item)]) built-mapping))]
    (mapv + maxXY maxLoc)
    )
  )