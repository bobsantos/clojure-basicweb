(ns com.bobsantosjr.basicweb.core
  (:require [secretary.core :as secretary]
            [goog.events :as events])
  (:require-macros [secretary.core :refer [defroute]])
  (:import [goog History]
           [goog.history EventType]))

(def app
  (js/document.getElementById "app"))

(defn set-html! [el content]
  (aset el "innerHTML" content))

(secretary/set-config! :prefix "#")

(defroute home-path "/" []
          (set-html! app "<h1>Home</h1>"))

(defroute "*" []
          (set-html! app "<h1>Page Not Found</h1>"))

(let [h (History.)]
  (events/listen h EventType.NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h
    (.setEnabled true)))

(defn init! []
  (secretary/dispatch! "/"))
