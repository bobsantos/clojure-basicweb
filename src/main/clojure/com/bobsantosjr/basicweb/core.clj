(ns com.bobsantosjr.basicweb.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [com.bobsantosjr.basicweb.healthcheck :as hc]
            [ring.middleware.params :as rp]
            [environ.core :refer [env]]))

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
  (let [port (Integer/parseInt (env :port))]
    (run-server app {:port port})
    (println (str "Server running on port " port))))