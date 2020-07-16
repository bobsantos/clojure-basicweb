(ns com.bobsantosjr.basicweb.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.params :as rp]
            [ring.middleware.cors :refer [wrap-cors]]
            [environ.core :refer [env]]
            [com.bobsantosjr.basicweb.healthcheck :as hc]
            [com.bobsantosjr.basicweb.api :as api]))

(defroutes handler
           (GET "/" []
             (hc/new))
           (GET "/:id" [id]
             (hc/form id))
           (POST "/:id" req
             (hc/save-response! req))
           (GET "/:id/summary" [id]
             (hc/answers-summary id))
           (GET "/api/healthcheck" []
             {:status 200
              :headers {"Content-Type" "application/json"}
              :body (api/new)})
           (route/not-found "<h1>Page not found</h1>"))

(def app (-> handler
             rp/wrap-params
             (wrap-cors :access-control-allow-origin [#"http://localhost:3001"]
                        :access-control-allow-methods [:get :put :post :delete])))

(defn -main []
  (let [port (Integer/parseInt (env :port))]
    (run-server app {:port port})
    (println (str "Server running at http://localhost:" port))))