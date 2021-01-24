(ns app.recipes.views.publish-recipe
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Box Button Col Row]]
            [app.components.modal :refer [modal]]
            [app.components.form-group :refer [form-group]]
            [app.helpers :refer [format-price]]))

(defn publish-recipe
  []
  (let [initial-values {:price ""}
        values         (r/atom initial-values)
        open-modal     (fn [recipe]
                         (rf/dispatch [:toggle-modal :publish-recipe])
                         (reset! values recipe))
        publish        (fn [e {:keys [price]}]
                         (.preventDefault e)
                         (rf/dispatch [:publish-recipe {:price (js/parseInt price)}])
                         (reset! values initial-values))]
    (fn []
      (let [{:keys [public? price]} @(rf/subscribe [:recipe])]
        [:> Box
         (cond
           public? [:> Button {:on-click #(open-modal initial-values)}
                    (format-price price)]
           (not public?) [:> Button {:on-click #(open-modal {:price price})}
                          "Publish"])
         [modal {:modal-name :publish-recipe
                 :header     "Recipe"
                 :body       [:form {:on-submit #(publish % @values)}
                              [form-group {:id     :price
                                           :label  "Price (in cents)"
                                           :values values
                                           :type   "number"}]]
                 :footer     [:<>
                              (when public?
                                [:a {:href     "#"
                                     :on-click #(rf/dispatch [:unpublish-recipe])}
                                 "Unpublish"])
                              [:> Button {:on-click #(rf/dispatch [:toggle-modal])
                                          :variant  "light"}
                               "Cancel"]
                              [:> Button {:on-click #(publish % @values)}
                               "Publish"]]}]]))))
