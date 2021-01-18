(ns app.recipes.views.recipe-steps
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Box Button Typography Row Col]]
            ["styled-icons/fa-solid/Plus" :refer [Plus]]
            [app.components.form-group :refer [form-group]]
            [app.components.modal :refer [modal]]))

(defn recipe-steps
  []
  (let [initial-values {:id nil :desc ""}
        values         (r/atom initial-values)
        open-modal     (fn [step]
                         (rf/dispatch [:toggle-modal :step-editor])
                         (reset! values step))
        save           (fn [{:keys [id desc]}]
                         (rf/dispatch [:upsert-step {:id   (or id (keyword (str "step-" (random-uuid))))
                                                     :desc (str/trim desc)}])
                         (reset! values initial-values))]
    (fn []
      (let [steps   @(rf/subscribe [:steps])
            author? @(rf/subscribe [:author?])]
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
            "Steps"]]
          [:> Box {:my 15
                   :pl 10}
           [:> Button {:variant  "light"
                       :size     "sm"
                       :on-click #(open-modal initial-values)}
            [:> Plus {:size 12}]]]]
         [:> Box
          (for [{:keys [id desc] :as step} steps]
            ^{:key id}
            [:> Box {:py 10}
             [:> Row (when author?
                       {:class    "editable"
                        :on-click #(open-modal step)})
              [:> Col {:py 10}
               desc]]])
          (when author?
            [modal {:modal-name :step-editor
                    :header     "Step"
                    :body       [:<>
                                 [form-group {:id        :desc
                                              :label     "Description"
                                              :type      "text"
                                              :textarea? true
                                              :values    values}]]
                    :footer     [:<>
                                 (when-let [step-id (:id @values)]
                                   [:a {:href     "#"
                                        :on-click #(when (js/confirm "Are you sure?")
                                                     (rf/dispatch [:delete-step step-id]))}
                                    "Delete"])
                                 [:> Button {:variant  "light"
                                             :mx       10
                                             :on-click #(rf/dispatch [:toggle-modal])}
                                  "Cancel"]
                                 [:> Button {:on-click #(save @values)}
                                  "Save"]]}])]]))))
