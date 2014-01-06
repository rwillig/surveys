(ns surveys.rules  
  (:require
    [tailrecursion.castra :refer [ex auth *request* *session*]]))

(def allow (constantly true))
(def deny  #(throw (ex auth "Not Allowed.")))
(def self  #(or (= (:email @*session*) %1) (deny)))

(defn login [email pass]
  (if (and (= email "foop") (= pass "foop"))
    (swap! *session* assoc :email email)
    (throw (ex auth "Wrong email or password."))))
