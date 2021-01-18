(ns app.become-a-chef.views.agreement
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box Button]]
            [app.router :as router]
            [app.components.modal :refer [modal]]))

(defn agreement
  []
  (let [logged-in? @(rf/subscribe [:logged-in?])]
    (if logged-in?
      [:> Box {}
       [:> Button {:on-click #(rf/dispatch [:toggle-modal :agreement])}
        "Get Started"]
       [modal {:modal-name :agreement
               :header     "Become a Chef"
               :body       "I agree to cook"
               :footer     [:<>
                            [:> Button {:on-click #(rf/dispatch [:toggle-modal])}
                             "Cancel"]
                            [:> Button {:on-click #(rf/dispatch [:agree-to-cook])}
                             "Agree"]]}]]
      [:> Button {:as       "a"
                  :color    "white"
                  :href     (router/path-for :sign-up)
                  :on-click #(rf/dispatch [:active-nav :sign-up])}
       "Sign up"])))