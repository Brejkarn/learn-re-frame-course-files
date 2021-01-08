(ns app.nav.views.authenticated
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.nav.views.item :refer [item]]))

(defn set-active-nav
  [nav]
  (rf/dispatch [:active-nav nav]))

(defn authenticated
  []
  (let [active-nav @(rf/subscribe [:active-nav])
        nav-items  [{:id       :saved
                     :name     "Saved"
                     :href     "#saved"
                     :dispatch #(set-active-nav :saved)}
                    {:id       :recipes
                     :name     "Recipes"
                     :href     "#recipes"
                     :dispatch #(set-active-nav :recipes)}
                    {:id       :inbox
                     :name     "Inbox"
                     :href     "#inbox"
                     :dispatch #(set-active-nav :inbox)}
                    {:id       :become-a-chef
                     :name     "Chef"
                     :href     "#become-a-chef"
                     :dispatch #(set-active-nav :become-a-chef)}
                    {:id       :profile
                     :name     "Profile"
                     :href     "#profile"
                     :dispatch #(set-active-nav :profile)}]]
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
