(ns surveys.api
  (:refer-clojure :exclude [defn])
  (:require [surveys.rules :refer [allow deny]]
            [tailrecursion.castra :refer [defn]]))

(defn doit []
  {:rpc [(allow)]}
  "This came from the server.")
