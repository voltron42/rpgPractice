(ns clojurerpg.control
  (:import (clojure.lang PersistentArrayMap)))

(gen-class
  :name clojurerpg.control.Break
  :extends java.lang.Exception
  :state "state"
  :init init
  :prefix "break-")

(defn break-init [^PersistentArrayMap state]
  [[] (atom state)]
  )

(defn break-getState [this]
  @(.state this))

(gen-class
  :name clojurerpg.control.Continue
  :extends java.lang.Exception
  :state "state"
  :init init
  :prefix "continue-")

(defn continue-init [^PersistentArrayMap state]
  [[] (atom state)]
  )

(defn continue-getState [this]
  @(.state this))

(gen-class
  :name clojurerpg.control.Halt
  :extends java.lang.Exception)
