(ns app.recipes.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :save-recipe
  (fn [db [_ id]]
    (let [uid (get-in db [:auth :uid])]
      (-> db
          (update-in [:users uid :saved] conj id)
          (update-in [:recipes id :saved-count] inc)))))
