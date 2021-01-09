(ns app.nav.views.authenticated
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.nav.views.item :refer [item]]
            [app.router :as router]))

(defn authenticated
  []
  (let [active-page @(rf/subscribe [:active-nav])
        nav-items  [{:id       :saved
                     :name     "Saved"
                     :href     (router/path-for :saved)
                     :dispatch #(rf/dispatch [:active-nav :saved])}
                    {:id       :recipes
                     :name     "Recipes"
                     :href     (router/path-for :recipes)
                     :dispatch #(rf/dispatch [:active-nav :recipes])}
                    {:id       :inboxes
                     :name     "Inbox"
                     :href     (router/path-for :inboxes)
                     :dispatch #(rf/dispatch [:active-nav :inboxes])}
                    {:id       :become-a-chef
                     :name     "Chef"
                     :href     (router/path-for :become-a-chef)
                     :dispatch #(rf/dispatch [:active-nav :become-a-chef])}
                    {:id       :profile
                     :name     "Profile"
                     :href     (router/path-for :profile)
                     :dispatch #(rf/dispatch [:active-nav :profile])}]]
    [:> Box {:display         "flex"
             :justify-content "flex-end"
             :py              1}
     (for [{:keys [id name href dispatch]} nav-items]
       [item {:key        id
              :id         id
              :name       name
              :href       href
              :dispatch   dispatch
              :active-page active-page}])]))
