(ns com.bobsantosjr.basicweb.core
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [com.bobsantosjr.basicweb.api :as api]
            [cljs.core.async :refer [<!]])
  (:require-macros [secretary.core :refer [defroute]]
                   [cljs.core.async.macros :refer [go]])
  (:import [goog History]
           [goog.history EventType]))

(def app
  (js/document.getElementById "app"))

(defn set-html! [el content]
  (aset el "innerHTML" content))

(secretary/set-config! :prefix "#")

(defroute home-path "/" []
          (go (let [{:keys [body]} (<! (api/new))
                    {:keys [id]} body]
                (js/console.log (str "id: " id))
                (set-html! app (str "<h1>Health check form: <a href=\"http://localhost:3000/" id "\">" id "</a></h1>")))))

(defroute "*" []
          (set-html! app "<h1>Page Not Found</h1>"))

(let [h (History.)]
  (events/listen h EventType.NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h
    (.setEnabled true)))

(defn init! []
  (secretary/dispatch! "/"))
