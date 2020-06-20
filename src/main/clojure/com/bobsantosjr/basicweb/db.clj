(ns com.bobsantosjr.basicweb.db)

(def health-checks [])

(defn new
  "Add new health check into db"
  []
  (let [id (java.util.UUID/randomUUID)
        health-check {:id id}]
    (conj health-checks health-check)
    health-check))