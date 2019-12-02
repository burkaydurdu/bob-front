(ns bob-front.navigation.views
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [bob-front.navigation.subs]))

(defn navigation-panel
  [active-panel]
  (let [[panel panel-name] @active-panel]
    [:div.container
     (when panel
       [panel])]))

(defn main-panel []
  [navigation-panel (subscribe [:active-panel])])
