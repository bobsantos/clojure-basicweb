(ns com.bobsantosjr.basicweb.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]))

(defn new []
  (http/get "http://localhost:3000/api/healthcheck" {:with-credentials? false :headers {"Content-Type" "application/json"}}))