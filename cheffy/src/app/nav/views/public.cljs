(ns app.nav.views.public
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.nav.views.item :refer [item]]))

(defn set-active-nav
  [nav]
  (rf/dispatch [:active-nav nav]))

(defn public
  []
  (let [active-nav @(rf/subscribe [:active-nav])
        nav-items  [{:id       :recipes
                     :name     "Recipes"
                     :href     "#recipes"
                     :dispatch #(set-active-nav :recipes)}
                    {:id       :become-a-chef
                     :name     "Chef"
                     :href     "#become-a-chef"
                     :dispatch #(set-active-nav :become-a-chef)}
                    {:id       :sign-up
                     :name     "Sign Up"
                     :href     "#sign-up"
                     :dispatch #(set-active-nav :sign-up)}
                    {:id       :log-in
                     :name     "Log In"
                     :href     "#log-in"
                     :dispatch #(set-active-nav :log-in)}]]
    [:> Box {:display         "flex"
             :justify-content "flex-end"
             :py              1}
     (for [{:keys [id name href dispatch]} nav-items]
       [item {:key        id
              :id         id
              :name       name
              :href       href
              :dispatch   dispatch
              :active-nav active-nav}])]))
