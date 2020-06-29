(ns com.bobsantosjr.basicweb.healthcheck
  (:require [com.bobsantosjr.basicweb.db :as db]
            [hiccup.core :refer [html]]
            [hiccup.form :as f]
            [hiccup.element :refer [link-to]]))

(def ratings ["Awesome" "Meh" "Disaster"])
(def perspectives [{:id "etr" :title "Easy to Release" :awesome "Releasing is simple, safe, painless & mostly automated." :disaster "Releasing is risky, painful, lots of manual work, and takes forever."}
                   {:id "su" :title "Suitable" :awesome "Our way of working fits us perfectly." :disaster "Our way of working sucks."}
                   {:id "tq" :title "Tech quality (code base health)" :awesome "We’re proud of the quality of our code! It is clean, easy to read, and has great test coverage." :disaster "Our code is a pile of dung, and technical debt is raging out of control."}
                   {:id "va" :title "Value" :awesome "We deliver great stuff! We’re proud of it and our stakeholders are really happy." :disaster "We deliver crap. We feel ashamed to deliver it. Our stakeholders hate us."}
                   {:id "sp" :title "Speed" :awesome "We get stuff done really quickly.No waiting, no delays." :disaster "We never seem to get done with anything.We keep getting stuck or interrupted. Stories keep getting stuck on dependencies."}
                   {:id "mi" :title "Mission" :awesome "We know exactly why we are here, and we are really excited about it." :disaster "We have no idea why we are here, there is no high level picture or focus. Our so-called mission is completely unclear and uninspiring."}
                   {:id "fu" :title "Fun" :awesome "We love going to work, and have great fun working together." :disaster "Boooooooring."}
                   {:id "le" :title "Learning" :awesome "We’re learning lots of interesting stuff all the time!" :disaster "We never have time to learn anything"}
                   {:id "sup" :title "Support" :awesome "We always get great support & help when we ask for it!" :disaster "We keep getting stuck because we can’t get the support & help that we ask for."}
                   {:id "pp" :title "Pawns or players" :awesome "We are in control of our destiny! We decide what to build and how to build it." :disaster "We are just pawns in a game of chess, with no influence over what we build or how we build it."}])

(defn- page
  "Main layout for the site"
  [title & content]
  (html [:head
         [:title title]]
        [:body
         [:section content]]))

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
   (labeled-radio-group (str id "-r") ratings "Rating")
   [:div
    [:p [:strong "Comment"]]
    [:p (f/text-area {} (str id "-c") "")]]])

(defn new
  "Create health check and return html"
  []
  (let [id (db/new)]
    (page "Health check form link"
          [:h1 "Health check form: " [:a {:href id} id]])))

(defn form
  "Returns the health check form"
  [id]
  (let [health-check (db/get-by-id id)]
    (if (nil? health-check)
      (page "Not found" [:h1 "Health check not found"])
      (page "Health check form"
            [:div
             [:p
              (link-to (str "/" id "/summary") "Summary")]
             [:div
              [:form {:action (str "/" id) :method "post"}
               (for [p perspectives]
                 (perspective p))
               [:div]
               [:div (f/submit-button {} "Submit")]]]]))))

(defn answers-summary
  "Return a summary of answers for a form"
  [id]
  (let [health-check (db/get-by-id id)
        page-title "Health check summary"]
    (if (nil? health-check)
      (page "Not found" [:h1 "Health check not found"])
      (if (empty? health-check)
        (page page-title
              [:h2 "No answers yet"])
        (page "Health check summary"
              [:h2 "Answers summary"])))))

(defn- summary-perspective-title
  [key]
  (let [title (-> (filter #(= (% :id) (name key)) perspectives)
                  first
                  (:title))]
    [:h2 title]))

(defn- summary-perspective-content
  [k v]
  (let [title (clojure.string/capitalize (name k))]
    [:p
     [:strong title] ": " [:em v]]))

(defn save-response!
  "Saves the health check answer to correct health check form"
  [{:keys [params]}]
  (let [answer-map (db/save-response (:id params) (dissoc params :id))]
    (page (str (:id params) "answer summary")
          [:div
           [:h1 "Summary"]
           (for [[k v] answer-map]
             [:div
              (summary-perspective-title k)
              (for [[x y] v]
                (summary-perspective-content x y))])])))