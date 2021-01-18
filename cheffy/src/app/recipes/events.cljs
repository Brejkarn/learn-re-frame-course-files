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
       :dispatch [:toggle-modal]})))

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
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :delete-step
  (fn [{:keys [db]} [_ step-id]]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (update-in db [:recipes recipe-id :steps] dissoc step-id)
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :upsert-step
  (fn [{:keys [db]} [_ {:keys [id desc]}]]
    (let [recipe-id (get-in db [:nav :active-recipe])
          steps     (get-in db [:recipes recipe-id :steps])
          order     (or (get-in steps [id :order])
                        (inc (count steps)))]
      {:db       (assoc-in db [:recipes recipe-id :steps id] {:id    id
                                                              :desc  desc
                                                              :order order})
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :delete-recipe
  (fn [{:keys [db]} _]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (update-in db [:recipes] dissoc recipe-id)
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :upsert-recipe
  (fn [{:keys [db]} [_ {:keys [name prep-time]}]]
    (let [recipe-id (get-in db [:nav :active-recipe])
          id        (or recipe-id (keyword (str "recipe-" (random-uuid))))
          uid       (get-in db [:auth :uid])]
      {:db          (update-in db [:recipes recipe-id] merge {:id        id
                                                              :cook      uid
                                                              :name      name
                                                              :prep-time prep-time
                                                              :public?   false})
       :dispatch-n  [[:active-page :recipes]
                     [:toggle-modal]]
       :navigate-to {:path "/recipes/"}})))

(reg-event-fx
  :publish-recipe
  (fn [{:keys [db]} [_ {:keys [price]}]]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (update-in db [:recipes recipe-id] merge {:price   price
                                                           :public? true})
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :unpublish-recipe
  (fn [{:keys [db]} _]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (assoc-in db [:recipes recipe-id :public?] false)
       :dispatch [:toggle-modal]})))

(reg-event-fx
  :upsert-image
  (fn [{:keys [db]} [_ {:keys [img]}]]
    (let [recipe-id (get-in db [:nav :active-recipe])]
      {:db       (assoc-in db [:recipes recipe-id :img] img)
       :dispatch [:toggle-modal]})))
