(ns app.auth.views.log-in
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Row Col Box Button FormGroup Label Input]]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]))

(defn log-in
  []
  (let [initial-values {:email    ""
                        :password ""}
        values         (r/atom initial-values)]
    (fn []
      [:> Row {:justify-content "center"}
       [:> Col {:xs 12 :sm 6}
        [page-nav {:center "Log In"}]
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
               :on-click #(rf/dispatch [:active-nav :sign-up])}
           "New chef? Create an account!"]]
         [:> Box
          [:> Button {:variant    "primary"
                      :aria-label "Log In!"
                      :on-click   #(rf/dispatch [:log-in @values])} "Log in"]]]]])))
