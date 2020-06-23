(ns com.bobsantosjr.basicweb.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [com.bobsantosjr.basicweb.healthcheck :as hc]
            [ring.middleware.params :as rp]))

(defroutes handler
           (GET "/" []
             (hc/new))
           (GET "/:id" [id]
             (hc/form id))
           (POST "/:id" req
             (hc/save-response! req))
           (route/not-found "<h1>Page not found</h1>"))

(def app (-> handler
             rp/wrap-params))

(defn -main []
  (run-server app {:port 8080})
  (println "Server running on port 8080"))