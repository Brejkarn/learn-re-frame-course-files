(ns app.inbox.views.inboxes
  (:require [re-frame.core :as rf]
            ["@smooth-ui/core-sc" :refer [Box]]
            [app.components.page-nav :refer [page-nav]]
            [app.inbox.views.inbox-card :refer [inbox-card]]))

(defn inboxes
  []
  (let [inboxes @(rf/subscribe [:user-inboxes])]
    [:> Box
     [page-nav {:center "Inboxes"
                :right  "right"}]
     [:> Box {:class "cards"}
      (for [[k {:keys [id notifications updated-at]}] inboxes
            :let [notifications? (> notifications 0)]]
            ^{:key id}
            [inbox-card {:uid-inbox      k
                         :inbox-id       id
                         :notifications? notifications?
                         :notifications  notifications
                         :updated-at     updated-at}])]]))
