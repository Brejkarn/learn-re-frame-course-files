(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Button Col Grid Normalize Row ThemeProvider]]

            [app.db]
            [app.router :as router]
            [app.theme :refer [app-theme]]

            [app.auth.events]
            [app.auth.subs]
            [app.auth.views.profile :refer [profile]]
            [app.auth.views.sign-up :refer [sign-up]]
            [app.auth.views.log-in :refer [log-in]]

            [app.become-a-chef.views.become-a-chef :refer [become-a-chef]]
            [app.become-a-chef.events]

            [app.inbox.views.inboxes :refer [inboxes]]

            [app.recipes.views.recipes :refer [recipes]]
            [app.recipes.views.recipe :refer [recipe]]
            [app.recipes.views.saved :refer [saved]]
            [app.recipes.events]
            [app.recipes.subs]

            [app.nav.views.nav :refer [nav]]
            [app.nav.events]
            [app.nav.subs]))

(defn pages
  [page-name]
  (case page-name
    :profile [profile]
    :sign-up [sign-up]
    :log-in [log-in]
    :become-a-chef [become-a-chef]
    :inboxes [inboxes]
    :recipes [recipes]
    :recipe [recipe]
    :saved [saved]
    [recipes]))

(defn app
  []
  (let [active-page @(rf/subscribe [:active-page])]
    [:<>
     [:> Normalize]
     [:> ThemeProvider {:theme app-theme}
      [:> Grid {:fluid false :max-width 768}
       [:> Row
        [:> Col
         [nav]
         [pages active-page]]]]]]))

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "app")))

(defn ^:export init
  []
  (router/start!)
  (rf/dispatch-sync [:initialize-db])
  (start))
