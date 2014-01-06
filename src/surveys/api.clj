(ns surveys.api
  (:refer-clojure :exclude [defn])
  (:require [suveys.rules :refer [allow deny]])
  (:require [tailrecursion.castra :refer [defn]])
)

(defn doit []
  {:rpc [(allow)]}
  "This came from the server.")