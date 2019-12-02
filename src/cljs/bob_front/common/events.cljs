(ns bob-front.common.events
  (:require [re-frame.core :refer [reg-event-fx]]
            [bob-front.db :as db]))

(reg-event-fx
 :initialise-db
 (fn [{:keys [db]} _]
   {:db (assoc db/default-db :name "Example")}))
