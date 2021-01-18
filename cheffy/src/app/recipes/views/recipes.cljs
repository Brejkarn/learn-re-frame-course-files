(ns app.recipes.views.recipes
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Typography]]
            [app.components.page-nav :refer [page-nav]]
            [app.recipes.views.recipe-list :refer [recipe-list]]
            [app.recipes.views.recipe-editor :refer [recipe-editor]]))

(defn recipes
  []
  (let [drafts     @(rf/subscribe [:drafts])
        public     @(rf/subscribe [:public])
        logged-in? @(rf/subscribe [:logged-in?])]
    [:<>
     [page-nav {:center "Recipes"
                :right  (when logged-in? [recipe-editor])}]
     (when (seq drafts)
       [:<>
        [:> Typography {:variant     "h4"
                        :py          20
                        :font-weight 700}
         "Drafts"]
        [recipe-list drafts]])
     (when (and logged-in? (seq public))
       [:<>
        [:> Typography {:variant     "h4"
                        :py          20
                        :font-weight 700}
         "Public"]
        [recipe-list public]])]))
