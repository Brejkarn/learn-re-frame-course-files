(ns app.nav.views.item
  (:require ["@smooth-ui/core-sc" :refer [Box]]))

(defn item
  [{:keys [active-page dispatch href id name]}]
  [:> Box {:as            "a"
           :href          href
           :key           id
           :ml            2
           :on-click      dispatch
           :pb            10
           :border-bottom (when (= active-page id) 2)}
   name])
