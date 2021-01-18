(ns app.become-a-chef.events
  (:require [re-frame.core :refer [reg-event-fx]]))

(reg-event-fx
  :agree-to-cook
  (fn [{:keys [db]} _]
    (let [uid (get-in db [:auth :uid])]
      {:db          (assoc-in db [:users uid :role] :chef)
       :dispatch-n  [[:toggle-modal]
                     [:active-nav :recipes]
                     [:active-page :recipes]]
       :navigate-to {:path "/recipes"}})))
