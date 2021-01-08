(ns app.auth.views.profile
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Row Col Box Button Typography]]
            [app.components.form-group :refer [form-group]]
            [app.components.page-nav :refer [page-nav]]))

(defn profile
  []
  (let [{:keys [first-name last-name]} @(rf/subscribe [:active-user-profile])
        initial-values {:first-name first-name
                        :last-name  last-name}
        values         (r/atom initial-values)]
    [:<>
     [page-nav {:center "profile"
                :right  [:> Button {:variant  "light"
                                    :on-click #(rf/dispatch [:log-out])} "Log Out"]}]
     [:> Row {:justify-content "center"}
      [:> Col {:xs 12 :sm 6}
       [:> Box {:background-color "white"
                :border-radius    10
                :p                3
                :pt               1}
        [:> Typography {:variant     "h4"
                        :py          10
                        :font-weight 700}
         "Personal Info"]
        [form-group {:id     :first-name
                     :label  "First Name"
                     :type   "text"
                     :values values}]
        [form-group {:id     :last-name
                     :label  "Last Name"
                     :type   "text"
                     :values values}]
        [:> Box {:display         "flex"
                 :justify-content "flex-end"}
         [:> Button {:on-click #(rf/dispatch [:update-profile @values])} "Update Profile"]]]
       [:> Box {:background-color "white"
                :border-radius    10
                :p                3
                :pt               1
                :mt               40}
        [:> Typography {:variant     "h4"
                        :py          10
                        :font-weight 700}
         "Danger Zone"]
        [:> Box {:display         "flex"
                 :justify-content "flex-end"}
         [:> Button {:variant  "danger"
                     :on-click #(when (js/confirm (str "Do you want to delete the account \"" first-name " " last-name "\"?"))
                                  (rf/dispatch [:delete-profile @values]))} "Delete Profile"]]]]]]))
