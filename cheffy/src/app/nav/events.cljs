(ns app.nav.events
  (:require [re-frame.core :refer [reg-event-db reg-fx]]
            [app.router :as router]))

(reg-fx
  :navigate-to
  (fn [{:keys [path]}]
    (router/set-token! path)))

(reg-event-db
  :route-change
  (fn [db [_ {:keys [handler]}]]
    (assoc-in db [:nav :active-page] handler)))

(reg-event-db
  :active-nav
  (fn [db [_ active-nav]]
    (assoc-in db [:nav :active-nav] active-nav)))

(reg-event-db
  :active-page
  (fn [db [_ active-page]]
    (assoc-in db [:nav :active-page] active-page)))
