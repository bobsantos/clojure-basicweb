(ns com.bobsantosjr.basicweb.healthcheck
  (:require [com.bobsantosjr.basicweb.db :as db]
            [hiccup.core :refer [html]]
            [hiccup.form :as f]))

(def ratings ["Awesome" "Meh" "Disaster"])
(def perspectives [{:id "e-t-r" :title "Easy to Release" :awesome "Releasing is simple, safe, painless & mostly automated." :disaster "Releasing is risky, painful, lots of manual work, and takes forever."}
                   {:id "su" :title "Suitable" :awesome "Our way of working fits us perfectly" :disaster "Our way of working sucks"}])

(defn- page
  "Main layout for the site"
  [title & content]
  (html [:head
         [:title title]]
        [:body
         [:div] content]))

(defn- labeled-radio-group
  [group-name values label]
  [:div
   [:span [:strong label]
    (for [r values]
      [:label {:for (str group-name "-" r)} (f/radio-button {} group-name false r) r])]])

(defn- perspective
  "Creates a perspective section"
  [{:keys [id title awesome disaster]}]
  [:div
   [:h2 title]
   [:p [:em (str "Awesome: " awesome)]]
   [:p [:em (str "Disaster: " disaster)]]
   (labeled-radio-group (str "rb-" id) ratings "Rating")
   [:div
    [:p [:strong "Comment"]]
    [:p (f/text-area {} (str "ta-" id) "")]]])

(defn new
  "Create health check and return html"
  []
  (let [{:keys [id]} (db/new)]
    (page "Health check form link"
          [:h1 "Health check form: " [:a {:href id} id]])))

(defn form
  "Returns the health check form"
  [id]
  (let [{:keys [id] :as health-check} (db/get-by-id id)]
    (page "Health check form"
          (f/form-to [:post (str "/" id)]
                     (for [p perspectives]
                       (perspective p))
                     [:div]
                     [:div (f/submit-button {} "Submit")]))))