(ns app.nav.events
  (:require [re-frame.core :refer [reg-event-db reg-fx]]
            [app.router :as router]))

(reg-fx
  :navigate-to
  (fn [{:keys [path]}]
    (router/set-token! path)))

(reg-event-db
  :route-change
  (fn [db [_ {:keys [handler route-params]}]]
    (-> db
        (assoc-in [:nav :active-page] handler)
        (assoc-in [:nav :active-recipe] (keyword (:recipe-id route-params)))
        (assoc-in [:nav :active-inbox] (keyword (:inbox-id route-params))))))

(reg-event-db
  :active-nav
  (fn [db [_ active-nav]]
    (assoc-in db [:nav :active-nav] active-nav)))

(reg-event-db
  :active-page
  (fn [db [_ active-page]]
    (assoc-in db [:nav :active-page] active-page)))

(reg-event-db
  :active-inbox
  (fn [db [_ active-inbox]]
    (assoc-in db [:nav :active-inbox] active-inbox)))

(reg-event-db
  :toggle-modal
  (fn [db [_ modal-name]]
    (assoc-in db [:nav :active-modal] modal-name)))