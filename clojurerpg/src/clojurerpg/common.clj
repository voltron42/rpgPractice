(ns clojurerpg.common)

(defn expressionDecoder [opmap]
  (fn expResolver [exp]
    (cond
      (coll? exp) (let [funckey (first exp)
                        func (if (and (symbol? funckey) (contains? opmap funckey))
                                 (opmap funckey)
                                   (throw (Exception. (format "'%s' is not a valid operator" funckey))))
                        params (map #(cons % [(expResolver %)]) (rest exp))]
                    (fn recursiveResolve [state]
                      (apply func (map (fn [[name arg]] #(arg state)) params))))
      (or (symbol? exp) (keyword? exp)) #(exp %)
      (or (number? exp) (string? exp) (= Boolean (type exp))) (constantly exp)
      :else (throw (Exception. (format "'%s' is not a valid expression" exp))))))

(defn functify [& funcs]
  (let [composite (apply partial funcs)]
    (fn [& args]
      (composite
        (map #(%) args)))))

(defn build-opmap [operators]
  (cond (seq? operators) (build-opmap (reduce #(assoc %1 %2 (resolve %2)) {} operators))
        (map? operators) (reduce (fn [out [k v]] (assoc out k (functify apply v))) {} operators)))
