(ns app.recipes.views.recipe-image
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Box Button Typography Modal ModalDialog ModalContent ModalBody ModalFooter]]
            [app.components.form-group :refer [form-group]]
            [app.components.modal :refer [modal]]
            ))

(defn recipe-image
  []
  (let [initial-values {:img ""}
        values         (r/atom initial-values)
        author?        @(rf/subscribe [:author?])
        save           (fn [e img]
                         (.preventDefault e)
                         (rf/dispatch [:upsert-image img])
                         (reset! values initial-values))
        open-modal     (fn [vals]
                         (rf/dispatch [:toggle-modal :image-editor])
                         (reset! values vals))]
    (fn []
      (let [{:keys [img name]} @(rf/subscribe [:recipe])]
        [:<>
         [:> Box {:class            (when author? "editable")
                  :background-image (str "url(" (or img "/img/placeholder.jpg") ")")
                  :background-size  "cover"
                  :min-height       400
                  :border-radius    10
                  :alt              name
                  :on-click         #(open-modal {:img img})}]
         (when author?
           [modal {:modal-name :image-editor
                   :header     "Image"
                   :body       [:form {:on-submit #(save % @values)}
                                [form-group {:id     :img
                                             :label  "URL"
                                             :type   "text"
                                             :values values}]]
                   :footer     [:<>
                                [:> Button {:on-click #(rf/dispatch [:toggle-modal])
                                            :variant  "light"}
                                 "Cancel"]
                                [:> Button {:on-click #(save % @values)}
                                 "Save"]]}])]))))
