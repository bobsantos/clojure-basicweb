(ns com.bobsantosjr.basicweb.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [com.bobsantosjr.basicweb.healthcheck :as hc]))

(defroutes app
           (GET "/" []
             (let [{:keys [id]} (hc/new)]
               (str "<h1>Health check form: <a href=\"/" id "\">" id "</a>")))
           (GET "/:id" [id]
             (str "<h1>Welcome to health check " id "</h1>"))
           (route/not-found "<h1>Page not found</h1>"))

(defn -main []
  (run-server app {:port 3000})
  (println "Server running on port 3000"))