(ns clojurerpg.common)

(defn expressionDecoder [opmap]
  (fn expResolver [exp]
    (cond
      (coll? exp) (do
                    (println "compiling expression: " exp)
                      (let [funckey (first exp)
                              func (if (and (symbol? funckey) (contains? opmap funckey))
                                       (opmap funckey)
                                         (throw (Exception. (format "'%s' is not a valid operator" exp))))
                              params (map #(cons % [(expResolver %)]) (rest exp))]
                        (fn recursiveResolve [state]
                          (println "invoking func: " exp)
                          (apply func (map (fn [[name arg]]
                                           #(do (println "eval: " name " -- " state)
                                                (arg state)))
                                         params)))))
      (or (symbol? exp) (keyword? exp)) (do (println "key: " exp) #(do (println "resolving key: " exp) (exp %)))
      (or (number? exp) (string? exp) (= Boolean (type exp))) (do (println "primative: " exp ", type: " (type exp)) (constantly exp))
      :else (throw (Exception. (format "'%s' is not a valid expression" exp))))))

(defn functify [& funcs]
  (let [composite (apply partial funcs)]
    (fn [& args]
      (println "resolving functify")
      (composite
        (map #(%) args)))))

(defn build-opmap [operators]
  (cond (seq? operators) (build-opmap (reduce #(assoc %1 %2 (resolve %2)) {} operators))
        (map? operators) (reduce (fn [out [k v]] (assoc out k (functify apply v))) {} operators)))
