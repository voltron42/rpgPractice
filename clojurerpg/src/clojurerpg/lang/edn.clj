(ns clojurerpg.lang.edn
  (:require [clojure.spec.alpha :as s]))

(defn- no-ns []
  (fn [value]
    (nil? (namespace value))))

(defn- name-matches [pattern]
  (fn [value]
    (let [val-name (name value)]
      ((set (re-matches pattern val-name)) val-name))))

(s/def ::function-name #{'! '!?= '!nil? '$ '$#!+ '* '+ '++ '- '-> '/ '< '<- '<<
                             '<= '= '> '>= '>> '? '?= '__ 'and 'count
                             'd10 'd100 'd12 'd20 'd4 'd6 'd8 'empty? 'if 'in 'int
                             'keys 'list 'map 'nil? 'or 'peek 'pop 'push 'rand
                             'set 'vars})

(s/def ::number number?)

(s/def ::string string?)

(s/def ::boolean boolean?)

(s/def ::literal (s/or :num ::number
                       :str ::string
                       :bool ::boolean))

(s/def ::valid-name (s/and (name-matches #"[a-zA-Z]+([-][a-zA-Z])*") (no-ns)))

(s/def ::variable-name (s/and keyword? ::valid-name))

(s/def ::subroutine-name (s/and symbol? ::valid-name))

(s/def ::resolvable (s/or :var ::variable-name
                          :literal ::literal
                          :expression ::expression
                          :nil nil?))

(s/def ::expression (s/cat :func ::function-name
                           :args (s/* ::resolvable)))


(s/def ::assign (s/map-of ::variable-name ::resolvable))

(s/def ::init ::assign)

(s/def ::step ::assign)

(s/def ::while ::expression)

(s/def ::when-block (s/and vector?
                           (s/cat :check ::expression
                                  :actions (s/+ ::instruction))))

(s/def ::for-block (s/and vector?
                          (s/cat :for (s/keys :req-un [::while]
                                              :opt-un [::init ::step])
                                 :actions (s/+ ::instruction))))

(s/def ::block (s/or :when ::when-block
                     :for ::for-block))

(s/def ::instruction (s/or :print-line string?
                           :pause #{'...}
                           :subroutine-call ::subroutine-name
                           :print-var ::variable-name
                           :assign ::assign
                           :block ::block))

(s/def ::subroutine (s/and vector? (s/coll-of ::instruction)))

(s/def ::subroutine-declarations (s/map-of ::subroutine-name
                                           ::subroutine))
(s/def ::program (s/and vector?
                        (s/cat :def (s/? ::subroutine-declarations)
                          :steps (s/+ ::instruction))))

