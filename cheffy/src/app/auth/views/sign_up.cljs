(ns app.auth.views.sign-up
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Row Col Box Button FormGroup Label Input]]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]))

(defn sign-up
  []
  (let [initial-values {:first-name ""
                        :last-name ""
                        :email    ""
                        :password ""}
        values         (r/atom initial-values)]
    (fn []
      [:> Row {:justify-content "center"}
       [:> Col {:xs 12 :sm 6}
        [page-nav {:center "Sign Up"}]
        [form-group {:id     :first-name
                     :label  "First name"
                     :type   "text"
                     :values values}]
        [form-group {:id     :last-name
                     :label  "Last name"
                     :type   "text"
                     :values values}]
        [form-group {:id     :email
                     :label  "Email"
                     :type   "email"
                     :values values}]
        [form-group {:id     :password
                     :label  "Password"
                     :type   "password"
                     :values values}]
        [:> Box {:display         "flex"
                 :justify-content "space-between"}
         [:> Box {:py 1
                  :pr 1}
          [:a {:href     "#sign-up"
               :on-click #(rf/dispatch [:active-nav :log-in])}
           "Already have an account? Log in here!"]]
         [:> Box
          [:> Button {:on-click #(rf/dispatch [:sign-up @values])} "Sign up"]]]]])))
