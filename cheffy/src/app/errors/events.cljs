(ns app.errors.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx after reg-cofx]]
            [cljs.reader :refer [read-string]]))

(reg-event-db
  :has-value?
  (fn [db [_ id]]
    (assoc-in db [:errors id] "Can't be blank")))

(reg-event-db
  :clear-error
  (fn [db [_ id]]
    (update-in db [:errors] dissoc id)))
