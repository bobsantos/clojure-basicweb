(ns com.bobsantosjr.basicweb.db
  (:require [clojure.string :as string]))

(def state (atom []))

(comment
  [{:id "uuid"
    :answers [{:perspective1 {:rating "Awesome" :comment "Comment"}
               :perspective2 {:rating "Awesome" :comment "Comment"}}
              {:perspective1 {:rating "Awesome" :comment "Comment"}
               :perspective2 {:rating "Awesome" :comment "Comment"}}]}])

(defn new
  "Add new health check into db"
  []
  (let [health-check {:id (str (java.util.UUID/randomUUID))
                      :answers []}]
    (if (nil? @state)
      (reset! state [])
      (swap! state conj health-check))
    health-check))

(defn get-by-id
  "Get health check data by ID"
  [id]
  (->> @state
       (filter #(= id (% :id)))
       first))

(defn- save-response-into-state!
  "Save the response into state atom as a map {:healthcheck-id {:perspective1 {:rating rating :comment comment}} {:perspective2 {:rating rating :comment comment}}}"
  [form-key response]
  (doseq [[k v] response]
    (let [[perspective attr & _] (string/split (str k) #"-")
          perspective-attr (if (= "r" attr) :rating :comment)]
      (swap! state assoc-in [(keyword (str form-key)) (keyword perspective) (keyword perspective-attr)] v)))
  ((keyword (str form-key)) @state))

(defn save-response
  "Adds response to health check"
  [form-key response]
  (save-response-into-state! form-key response))