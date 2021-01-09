(ns app.nav.views.public
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.router :as router]
            [app.nav.views.item :refer [item]]))

(defn public
  []
  (let [active-page @(rf/subscribe [:active-page])
        nav-items   [{:id       :recipes
                      :name     "Recipes"
                      :href     (router/path-for :recipes)
                      :dispatch #(rf/dispatch [:active-nav :recipes])}
                     {:id       :become-a-chef
                      :name     "Chef"
                      :href     (router/path-for :become-a-chef)
                      :dispatch #(rf/dispatch [:active-nav :become-a-chef])}
                     {:id       :sign-up
                      :name     "Sign Up"
                      :href     (router/path-for :sign-up)
                      :dispatch #(rf/dispatch [:active-nav :sign-up])}
                     {:id       :log-in
                      :name     "Log In"
                      :href     (router/path-for :log-in)
                      :dispatch #(rf/dispatch [:active-nav :log-in])}]]
    [:> Box {:display         "flex"
             :justify-content "flex-end"
             :py              1}
     (for [{:keys [id name href dispatch]} nav-items]
       [item {:key         id
              :id          id
              :name        name
              :href        href
              :dispatch    dispatch
              :active-page active-page}])]))
