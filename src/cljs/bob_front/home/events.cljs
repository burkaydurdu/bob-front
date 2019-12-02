(ns bob-front.home.events
  (:require [re-frame.core :refer [reg-event-fx reg-event-db dispatch]]
            [bob-front.util :as util]))

(reg-event-fx
  :get-last-coffee
  (fn [{:keys [db]} _]
    {:http-xhrio (util/create-request-map :get "/coffee/last"
                                          :get-last-coffee-result-ok)}))

(reg-event-db
  :get-last-coffee-result-ok
  (fn [db [_ data]]
    (assoc-in db [:home :last-coffee-status] data )))

