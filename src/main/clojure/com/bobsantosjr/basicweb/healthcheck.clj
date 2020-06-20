(ns com.bobsantosjr.basicweb.healthcheck
  (:require [com.bobsantosjr.basicweb.db :as db]))

(defn new
  "Create health check"
  []
  (db/new))