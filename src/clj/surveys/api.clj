(ns surveys.api
  (:refer-clojure :exclude [defn])
  (:require [surveys.rules :as rules]
            [surveys.model :as model]
            [tailrecursion.castra :refer [defn *session*]]))

(defn doit []
  {:rpc [(rules/allow)]}
  "This came from the server.")

(defn get-state [& [email]]
  {:rpc [(or (not email) (rules/self email))]}
  (if-let [email (or email (get @*session* :email))]
    (assoc (get-in @model/db [:users email]) :email email)
    nil))

(defn login [email password]
  {:rpc [(rules/login email password)]}
  (get-state email))

(defn sign-up [first last company email pass pass2]
  {:rpc [(and
           (rules/required first last company email pass pass2)
           (rules/register email pass pass2))]}
  (swap! model/db update-in [:users email] assoc :first first :last last :company company)
  (get-state email))

(defn setup-company [email loc-country loc-state industry subindustry size frequency]
  {:rpc [(and
           (rules/self email)
           (rules/required email loc-country industry subindustry size frequency))]}
  (swap! model/db update-in [:users email] assoc
    :location-country loc-country :location-state loc-state :industry industry :subindustry subindustry :size size :frequency frequency :customers {})
  (get-state email))

(defn select-questions [email promptness accuracy helpfulness partnership]
  {:rpc [(and
           (rules/self email)
           (rules/required email promptness accuracy helpfulness partnership))]}
  (swap! model/db update-in [:users email] assoc
    :promptness promptness :accuracy accuracy :helpfulness helpfulness :partnership partnership)
  (get-state email))

(defn add-customer [email first last email* company group]
  {:rpc [(and
           (rules/self email)
           (rules/required email first last email*))]}
  (swap! model/db update-in [:users email :customers] assoc
    email* {:first first :last last :company company :group group})
  (get-state email))
