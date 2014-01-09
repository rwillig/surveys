(ns surveys.rules  
  (:require
   [clojure.string :as string]
   [surveys.model  :as model]
   [tailrecursion.castra :refer [ex auth error *request* *session*]]))

(def allow (constantly true))
(def deny  #(throw (ex auth "Not Allowed.")))
(def self  #(or (= (:email @*session*) %1) (deny)))

(defn required [& args]
  (or (not (some string/blank? args))
    (throw (ex error "Missing required field(s)."))))

(defn login [email pass]
  (if (let [u (get-in @model/db [:users email])] (and u (= (:pass u) pass)))
    (swap! *session* assoc :email email)
    (throw (ex auth "Wrong email or password."))))

(defn register [email pass pass2]
  (when (contains? (get @model/db :users) email)
    (throw (ex error "Email already exists in the system.")))
  (when (not= pass pass2)
    (throw (ex error "Passwords don't match.")))
  (swap! model/db update-in [:users] assoc email {:pass pass})
  (login email pass))
