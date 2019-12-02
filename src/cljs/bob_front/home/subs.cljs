(ns bob-front.home.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :home
  (fn [db]
    (:home db)))

(reg-sub
  :last-coffee-status
  :<- [:home]
  (fn [home]
    (:last-coffee-status home)))
