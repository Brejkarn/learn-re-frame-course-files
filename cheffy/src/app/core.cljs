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

            [app.inbox.views.inbox :refer [inbox]]

            [app.recipes.views.recipes :refer [recipes]]

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
    :inbox [inbox]
    :recipes [recipes]
    [recipes]))

(defn app
  []
  (let [active-nav @(rf/subscribe [:active-nav])]
    [:<>
     [:> Normalize]
     [:> ThemeProvider {:theme app-theme}
      [:> Grid {:fluid false}
       [:> Row
        [:> Col
         [nav]
         [pages active-nav]]]]]]))

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "app")))

(defn ^:export init
  []
  (router/start!)
  (rf/dispatch-sync [:initialize-db])
  (start))
