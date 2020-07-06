(ns com.bobsantosjr.basicweb.api
  (:require [cheshire.core :refer :all]
            [com.bobsantosjr.basicweb.db :as db]))

(defn new []
  (generate-string {:id (db/new)}))

