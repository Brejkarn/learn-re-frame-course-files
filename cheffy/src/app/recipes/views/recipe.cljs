(ns app.recipes.views.recipe
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box Col Row]]
            [app.components.page-nav :refer [page-nav]]
            [app.recipes.views.recipe-info :refer [recipe-info]]))

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
         "Recipe Image"]
        [:> Box {:pb 20}
         "Recipe Ingredients"]]
       [:> Col {:xs 12 :sm 6}
        [:> Box {:pb 20}
         "Recipe Steps"]]]]]))
