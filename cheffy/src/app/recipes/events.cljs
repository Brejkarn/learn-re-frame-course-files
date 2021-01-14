(ns app.recipes.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]))

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

(reg-event-fx
  :delete-ingredient
  (fn [{:keys [db]} [_ ingredient-id]]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (update-in db [:recipes recipe-id :ingredients] dissoc ingredient-id)
       :dispatch [:toggle-modal nil]})))

(reg-event-fx
  :upsert-ingredient
  (fn [{:keys [db]} [_ {:keys [id name amount measure]}]]
    (let [recipe-id   (get-in db [:nav :active-recipe])
          ingredients (get-in db [:recipes recipe-id])
          order       (or (get-in ingredients [id :order])
                          (inc (count ingredients)))]
      {:db       (assoc-in db [:recipes recipe-id :ingredients id] {:id      id
                                                                    :name    name
                                                                    :amount  amount
                                                                    :measure measure
                                                                    :order   order})
       :dispatch [:toggle-modal nil]})))