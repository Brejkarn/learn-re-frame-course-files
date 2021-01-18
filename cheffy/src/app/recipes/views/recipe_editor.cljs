(ns app.recipes.views.recipe-editor
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [clojure.string :as str]
            [app.components.modal :refer [modal]]
            [app.components.form-group :refer [form-group]]
            ["@smooth-ui/core-sc" :refer [Row Col Button Typography]]
            ["styled-icons/fa-solid/Plus" :refer [Plus]]))

(defn recipe-editor
  []
  (let [initial-values {:name "" :prep-time ""}
        values         (r/atom initial-values)
        open-modal     (fn [recipe]
                         (rf/dispatch [:toggle-modal :recipe-editor])
                         (reset! values recipe))
        save           (fn [{:keys [name prep-time]}]
                         (rf/dispatch [:upsert-recipe {:name      (str/trim name)
                                                       :prep-time (js/parseInt prep-time)}])
                         (reset! values initial-values))]
    (fn []
      (let [{:keys [name prep-time]} @(rf/subscribe [:recipe])
            active-page @(rf/subscribe [:active-page])]
        [:<>
         (if (= active-page :recipe)
           [:div {:class       "editable"
                  :font-weight 700
                  :on-click    #(open-modal {:name name :prep-time prep-time})}
            name]
           [:> Button {:on-click #(open-modal initial-values)}
            [:> Plus {:size 16}]])
         [modal {:modal-name :recipe-editor
                 :header     "Recipe"
                 :body       [:> Row
                              [:> Col
                               [form-group {:id     :name
                                            :label  "Name"
                                            :type   "text"
                                            :values values}]]
                              [:> Col {:xs 4}
                               [form-group {:id     :prep-time
                                            :label  "Cooking time (min)"
                                            :type   "number"
                                            :values values}]]]
                 :footer     [:<>
                              (when name
                                [:a {:href     "#"
                                     :on-click #(when (js/confirm "Are you sure?")
                                                  (rf/dispatch [:delete-recipe]))}
                                 "Delete"])
                              [:> Button {:variant  "light"
                                          :on-click #(rf/dispatch [:toggle-modal])}
                               "Cancel"]
                              [:> Button {:on-click #(save @values)}
                               "Save"]]}]]))))
