(ns bob-front.home.views
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [subscribe dispatch]]
    [bob-front.util :as util]
    [bob-front.home.subs]))


(defn coffee-status-title 
  [coffee-status]
  [:span.tag.is-info
   (case (:status coffee-status)
     "empty" "Kahve yok" 
     "enough" "Yeterince var"
     "brewing" "Demleniyor"
     "ready" "Hazır")])

(defn coffee-last-seen
  [coffee-status]
  [:span.tag.is-primary.coffee-margin-left-10
   (util/format-date (util/moment (:created_at coffee-status))
                     "DD/MM/YYYY HH:MM:SS")])

(defn coffee-number-of-coffees
  []
  (let [brewing-count @(subscribe [:brewing-coffee-count])]
   [:span.tag.is-warning.coffee-margin-left-10
    (str "Dünkü içilen "brewing-count)]))

(defn coffee-last-status-view []
  (let [coffee-status @(subscribe [:last-coffee-status])]
    (when coffee-status
      [:<>
       [:div
        [:img 
         {:width 320
          :src (case (:status coffee-status)
                 "empty" "img/empty.png"
                 "enough" "img/enough.png"
                 "brewing" "img/brewing.png"
                 "ready" "img/ready.png")}]]

       [:div.coffee-margin-top-10
        [coffee-status-title coffee-status]
        [coffee-last-seen coffee-status]
        [coffee-number-of-coffees]]])))

(defn home-header []
  [:h2.title.is-2 "Kahve Durumu"])

(defn home-body []
  [:div
   [coffee-last-status-view]])

(defn set-timer []
   (util/timer #(dispatch [:get-last-coffee]) (* 1000 60)))

(defn home
  []
  (reagent/create-class
    {:component-did-mount #(do (dispatch [:get-last-coffee])
                               (dispatch [:get-last-brewing-coffee])
                               (set-timer))
     :reagent-render (fn []
                       [:div.columns
                        [:div
                         {:class "column is-4 is-offset-4 is-10-mobile is-offset-1-mobile is-8-tablet is-offset-2-tablet"}
                         [home-header]
                         [home-body]]])}))
