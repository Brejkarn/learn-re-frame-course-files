(ns app.recipes.views.recipe-list
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.recipes.views.recipe-card :refer [recipe-card]]))

(defn recipe-list
  [recipes]
  [:> Box {:class "cards"}
   (for [recipe recipes]
     ^{:key (:id recipe)}
     [recipe-card recipe])])
