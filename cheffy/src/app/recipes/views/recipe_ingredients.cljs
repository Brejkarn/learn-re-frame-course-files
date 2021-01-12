(ns app.recipes.views.recipe-ingredients
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Box Button Typography Row Col]]
            ["styled-icons/fa-solid/Plus" :refer [Plus]]
            [app.components.form-group :refer [form-group]]
            [app.components.modal :refer [modal]]))

(defn recipe-ingredients
  []
  (let [initial-values {:id nil :name "" :amount 0 :measure ""}
        values         (r/atom initial-values)
        open-modal     (fn [vals]
                         (rf/dispatch [:toggle-modal :ingredients-editor])
                         (reset! values vals))
        save           (fn [{:keys [id name amount measure]}]
                         (rf/dispatch [:upsert-ingredient {:id      (or id (keyword (str "ingredient-" (random-uuid))))
                                                           :name    (str/trim name)
                                                           :amount  (js/parseInt amount)
                                                           :measure (str/trim measure)}])
                         (reset! values initial-values))
        ingredients    @(rf/subscribe [:ingredients])
        author?        @(rf/subscribe [:author?])]
    (fn []
      [:> Box {:p                2
               :background-color "white"
               :border-radius    10
               :pt               0}
       [:> Box {:display         "flex"
                :justify-content "flex-start"}
        [:> Box
         [:> Typography {:variant "h5"
                         :py      15
                         :m       0}
          "Ingredients"]]
        [:> Box {:my 15
                 :pl 10}
         [:> Button {:variant  "light"
                     :size     "sm"
                     :on-click #(open-modal initial-values)}
          [:> Plus {:size 12}]]]]
       [:> Box
        (for [{:keys [id amount measure name]} ingredients]
          ^{:key id}
          [:> Box {:py 10}
           [:> Row
            [:> Col {:xs 3}
             amount " " measure]
            [:> Col
             name]]])
        (when author?
          [modal {:modal-name :ingredients-editor
                  :header     "Ingredients"
                  :body       [:<>
                               [:> Row
                                [:> Col
                                 [form-group {:id     :amount
                                              :label  "Amount"
                                              :type   "number"
                                              :values values}]]
                                [:> Col
                                 [form-group {:id     :measure
                                              :label  "Measure"
                                              :type   "text"
                                              :values values}]]]
                               [form-group {:id     :name
                                            :label  "Name"
                                            :type   "text"
                                            :values values}]]
                  :footer     [:<>
                               (when-let [ingredient-id (:id @values)]
                                 [:a {:href     "#"
                                      :on-click #(when (js/confirm "Are you sure?")
                                                   (rf/dispatch [:delete-ingredient ingredient-id]))}
                                  "Delete"])
                               [:> Button {:variant  "light"
                                           :mx       10
                                           :on-click #(rf/dispatch [:toggle-modal nil])}
                                "Cancel"]
                               [:> Button {:on-click #(save @values)}
                                "Save"]]}])]])))
