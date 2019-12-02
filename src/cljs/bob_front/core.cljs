(ns bob-front.core
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [goog.dom :as dom]
   [bob-front.routes :refer [app-routes]]
   [bob-front.navigation.views :as views]))

(defn mount-root
  []
  (re-frame/clear-subscription-cache!)
  (r/render [views/main-panel] (dom/getElement "app")))

(defn ^:export init
  []
  (app-routes)
  (re-frame/dispatch-sync [:initialise-db])
  (mount-root))
