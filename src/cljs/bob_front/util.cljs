(ns bob-front.util
  (:require [ajax.core :as ajax]
            [cljsjs/moment]))

(goog-define timeout 3000)

(goog-define api-url "http://localhost:3000")

(defn create-request-map
  ([type uri]
   (create-request-map type uri nil nil))
  ([type uri on-success]
   (create-request-map type uri on-success nil))
  ([type uri on-success on-fail]
   (cond-> {:method          type
            :uri             (str api-url uri)
            :format          (ajax/json-request-format)
            :timeout         timeout
            :response-format (ajax/json-response-format {:keywords? true})
            :on-success      (if (vector? on-success) on-success [on-success])
            :on-failure      (if (vector? on-fail) on-fail [on-fail])}
           (nil? on-success) (assoc :on-success [:no-http-on-ok])
           (nil? on-fail) (assoc :on-failure [:no-http-on-failure]))))

(defn moment
  "Wraps a Moment object."
  ([] (js/moment))
  ([some-date] (js/moment some-date)))

(defn format-date
  [moment-date str-format]
  (.format moment-date str-format))

(defn timer
  [f ms]
  (js/setInterval f ms))
