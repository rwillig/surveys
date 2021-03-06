(ns surveys.core
  (:gen-class)
  (:require
    [ring.adapter.jetty              :refer [run-jetty]]
    [ring.middleware.resource        :refer [wrap-resource]]
    [ring.middleware.session         :refer [wrap-session]]
    [ring.middleware.session.cookie  :refer [cookie-store]]
    [ring.middleware.file-info       :refer [wrap-file-info]]
    [tailrecursion.castra.handler    :refer [castra]]))


(def app
  (->
    (castra  'surveys.api)
    (wrap-session {:store (cookie-store {:key "a 16-byte secret"})})
    (wrap-resource "public")    
    (wrap-file-info)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
