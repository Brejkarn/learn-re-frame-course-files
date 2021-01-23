(ns app.inbox.views.inbox
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.core :as r]
            ["@smooth-ui/core-sc" :refer [Row Col Box Button Input]]
            [app.components.page-nav :refer [page-nav]]
            [app.inbox.views.message-card :refer [message-card]]))

(defn inbox
  []
  (let [initial-values {:message ""}
        values         (r/atom initial-values)
        save           (fn [e {:keys [message]}]
                         (.preventDefault e)
                         (rf/dispatch [:insert-message {:message (str/trim message)}])
                         (reset! values initial-values))]
    (fn []
      (let [inbox-messages    @(rf/subscribe [:inbox-messages])
            conversation-with @(rf/subscribe [:conversation-with])]
        [:> Box
         [page-nav {:left   :inboxes
                    :center conversation-with}]
         [:> Row {:justify-content "center"}
          [:> Col {:xs 12 :md 6}
           [:> Box {:display "flex"}
            [:> Box {:width "100%"}
             [:form {:on-submit #(save % @values)}
              [:> Input {:control   true
                         :value     (:message @values)
                         :on-change #(swap! values assoc :message (.. % -target -value))}]]]
            [:> Box
             [:> Button {:size     "sm"
                         :mt       "6px"
                         :ml       -50
                         :on-click #(save % @values)}
              "Save"]]]
           [:> Box {:background    "white"
                    :border-radius 10
                    :mt            10}
            (when (seq inbox-messages)
              (for [{:keys [message author created-at] :as m} inbox-messages]
                ^{:key created-at}
                [message-card m]))]]]]))))
