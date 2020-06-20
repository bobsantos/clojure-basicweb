(ns com.bobsantosjr.basicweb.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [com.bobsantosjr.basicweb.healthcheck :as hc]))

(defroutes app
           (GET "/" []
             (hc/new))
           (GET "/:id" [id]
             (hc/form id))
           (route/not-found "<h1>Page not found</h1>"))

(defn -main []
  (run-server app {:port 3000})
  (println "Server running on port 3000"))