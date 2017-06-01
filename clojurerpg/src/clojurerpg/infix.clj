(ns clojurerpg.infix)

(def order-of-ops '[#{+ -} #{* /} #{pow root log} #{d}])

(defn reorder [exp])