(ns app.recipes.views.recipe
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box Col Row]]
            [app.components.page-nav :refer [page-nav]]
            [app.recipes.views.recipe-info :refer [recipe-info]]
            [app.recipes.views.recipe-image :refer [recipe-image]]
            [app.recipes.views.recipe-ingredients :refer [recipe-ingredients]]))

(defn recipe
  []
  (let [{:keys [name]} @(rf/subscribe [:recipe])]
    [:> Box
     [page-nav {:center name}]
     [:> Box
      [:> Row
       [:> Col {:xs 12 :sm 6}
        [:> Box {:pb 20}
         [recipe-info]]
        [:> Box {:pb 20}
         [recipe-image]]
        [:> Box {:pb 20}
         [recipe-ingredients]]]
       [:> Col {:xs 12 :sm 6}
        [:> Box {:pb 20}
         "Recipe Steps"]]]]]))
