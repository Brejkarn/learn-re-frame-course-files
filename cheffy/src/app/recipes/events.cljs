(ns app.recipes.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :toggle-saved-recipe
  (fn [db [_ id]]
    (let [uid                   (get-in db [:auth :uid])
          recipe-already-saved? (get-in db [:users uid :saved id])
          recipe-fn             (if recipe-already-saved? disj conj)
          recipe-count-fn       (if recipe-already-saved? dec inc)]
      (-> db
          (update-in [:users uid :saved] recipe-fn id)
          (update-in [:recipes id :saved-count] recipe-count-fn)))))
