(ns com.bobsantosjr.basicweb.db)

(def state (atom {}))

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

(defn- create-map-response
  [response]
  (reduce (fn [map-response [k v]]
            (let [[perspective attr & _] (clojure.string/split (str k) #"-")
                  perspective-attr (if (= "r" attr) :rating :comment)]
              (assoc-in map-response [(keyword perspective) (keyword perspective-attr)] v)))
          {}
          response))

(defn- save-response-into-state!
  "Save the response into state atom"
  [form-key response]
  (let [map-response (create-map-response response)]
    (swap! state update-in [(keyword form-key)] conj map-response)
    map-response))

(defn save-response
  "Adds response to health check"
  [form-key response]
  (save-response-into-state! form-key response))

(defn reduce-on-keys
  "Reduce values of vector of maps into lists based on keys"
  [coll]
  (reduce (fn [r el]
            (reduce (fn [ir [k v]] (update-in ir [k] (fnil conj []) v)) r el))
          {}
          coll))

(defn get-summary
  "Gets the summary of the answers in health-check-summary format"
  [id]
  (let [health-check (reduce-on-keys (get-by-id id))]
    (reduce (fn [r [k v]]
              (assoc-in r [k] (reduce-on-keys v)))
            {}
            health-check)))

(comment
  (def health-check-record {:c2457ed9-22eb-455b-9cf3-281d3787c4b8 [{:perspective1 {:rating "Awesome" :comment "Comment"}
                                                                    :perspective2 {:rating "Awesome" :comment "Comment"}}
                                                                   {:perspective1 {:rating "Awesome" :comment "Comment"}
                                                                    :perspective2 {:rating "Awesome" :comment "Comment"}}]
                            :66afddb2-9d36-4e94-9fb2-3f8555b473bf [{:perspective1 {:rating "Awesome" :comment "Comment"}
                                                                    :perspective2 {:rating "Awesome" :comment "Comment"}}
                                                                   {:perspective1 {:rating "Awesome" :comment "Comment"}
                                                                    :perspective2 {:rating "Awesome" :comment "Comment"}}]}))

(comment
  (def health-check-summary {:perspective1 {:rating [] :comment []}
                             :perspective2 {:rating [] :comment []}}))