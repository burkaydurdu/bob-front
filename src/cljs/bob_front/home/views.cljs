(ns bob-front.home.views
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [subscribe dispatch]]
    [bob-front.util :as util]
    [bob-front.home.subs]))

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
        [:span.tag.is-info
         (case (:status coffee-status)
           "empty" "Kahve yok" 
           "enough" "Yeterince var"
           "brewing" "Demleniyor"
           "ready" "Hazır")]
        [:span.tag.is-primary.coffee.margin-left-10
         (util/format-date (util/moment (:created_at coffee-status))
                           "DD/MM/YYYY HH:MM:SS")]]])))

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
                               (set-timer))
     :reagent-render (fn []
                       [:div.columns.is-mobile
                        [:div.column.is-half.is-offset-one-quarter
                         [home-header]
                         [home-body]]])}))
