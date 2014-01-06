(ns surveys.api
  (:refer-clojure :exclude [defn])
  (:require [surveys.rules :as rules]
            [tailrecursion.castra :refer [defn]]))

(defn doit []
  {:rpc [(rules/allow)]}
  "This came from the server.")

(defn get-state [& [email]]
  {:rpc [(or (not email) (rules/self email))]}
  (if-not email
    nil
    {:hi :world}))

(defn login [email password]
  {:rpc [(rules/login email password)]}
  (get-state email))
