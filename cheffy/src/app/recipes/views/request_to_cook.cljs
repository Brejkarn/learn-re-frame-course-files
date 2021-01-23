(ns app.recipes.views.request-to-cook
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Box Button Col Row]]
            [app.components.modal :refer [modal]]
            [app.components.form-group :refer [form-group]]
            [app.helpers :refer [format-price]]))

(defn request-to-cook
  []
  (let [initial-values {:message ""}
        values         (r/atom initial-values)
        open-modal     (fn [recipe]
                         (rf/dispatch [:toggle-modal :request-to-cook])
                         (reset! values recipe))
        request        (fn [{:keys [message]}]
                         (rf/dispatch [:request-message {:message (str/trim message)}])
                         (reset! values initial-values))]
    (fn []
      (let [{:keys [price]} @(rf/subscribe [:recipe])]
        [:> Box
         [:> Button {:on-click #(open-modal initial-values)}
                     "Order for " (format-price price)]
         [modal {:modal-name :request-to-cook
                 :header     "Order"
                 :body       [form-group {:id     :message
                                          :label  "Event desc"
                                          :values values
                                          :type   "text"}]
                 :footer     [:<>
                              [:> Button {:on-click #(rf/dispatch [:toggle-modal])
                                          :variant  "light"}
                               "Cancel"]
                              [:> Button {:on-click #(request @values)}
                               "Order"]]}]]))))
