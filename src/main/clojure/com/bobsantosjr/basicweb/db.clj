(ns com.bobsantosjr.basicweb.db
  (:require [clojure.string :as string]
            [clojure.pprint :as pretty]))

(def state (atom {}))

(defn new
  "Add new health check into db"
  []
  (let [id (java.util.UUID/randomUUID)
        health-check {:id id}]
    (if (nil? @state)
      (reset! state  {(keyword (str (java.util.UUID/randomUUID))) {}} )
      (swap! state assoc (keyword (str id)) {}))
    health-check))

(defn get-by-id
  "Get health check data by ID"
  [id]
  ((keyword id) @state))

(defn- map-response
  "Map the response"
  [response]
  (let [response-map {}]
    (doseq [[k v] response]
      (let [[perspective attr & _] (string/split (str k) #"-")
            perspective-attr (if (= "r" attr) :rating :comment)]
        (assoc-in response-map [perspective perspective-attr] v)))
    response-map))

(defn save-response
  "Adds response to health check"
  [id response]
  (let [k (keyword id)
        response-map (map-response response)]
    (swap! state update-in [k] merge response-map)
    (pretty/pprint response-map)
    response-map))