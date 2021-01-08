(ns app.nav.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :active-nav
  (fn [db [_ nav]]
    (assoc-in db [:nav :active-nav] nav)))
