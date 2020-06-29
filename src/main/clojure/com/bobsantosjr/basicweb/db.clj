(ns com.bobsantosjr.basicweb.db
  (:require [clojure.string :as string]))

(def state (atom {}))

(comment
  {:c2457ed9-22eb-455b-9cf3-281d3787c4b8 [{:perspective1 {:rating "Awesome" :comment "Comment"}
                                           :perspective2 {:rating "Awesome" :comment "Comment"}}
                                          {:perspective1 {:rating "Awesome" :comment "Comment"}
                                           :perspective2 {:rating "Awesome" :comment "Comment"}}]
   :66afddb2-9d36-4e94-9fb2-3f8555b473bf [{:perspective1 {:rating "Awesome" :comment "Comment"}
                                           :perspective2 {:rating "Awesome" :comment "Comment"}}
                                          {:perspective1 {:rating "Awesome" :comment "Comment"}
                                           :perspective2 {:rating "Awesome" :comment "Comment"}}]})

(defn new
  "Add new health check into db"
  []
  (let [id (keyword (str (java.util.UUID/randomUUID)))]
    (swap! state conj {id []})
    (name id)))

(defn get-by-id
  "Get health check data by ID"
  [id]
  (@state (keyword id)))

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