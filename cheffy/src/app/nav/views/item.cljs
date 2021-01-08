(ns app.nav.views.item
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]))

(defn item
  [{:keys [active-nav dispatch href id name]}]
  [:> Box {:as            "a"
           :href          href
           :key           id
           :ml            2
           :on-click      dispatch
           :pb            10
           :border-bottom (when (= active-nav id) 2)}
   name])
