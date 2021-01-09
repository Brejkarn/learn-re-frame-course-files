(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx after reg-cofx]]
            [cljs.reader :refer [read-string]]))

(def session-key "app-session")

(defn set-user-session!
  [{:keys [auth]}]
  (when (:uid auth) (.setItem js/localStorage session-key (str auth))))

(defn remove-user-session!
  []
  (.removeItem js/localStorage session-key))

(def set-user-interceptor [(after set-user-session!)])
(def remove-user-interceptor [(after remove-user-session!)])

(reg-cofx
  :session-user
  (fn [cofx _]
    (assoc cofx :session-user (read-string (.getItem js/localStorage session-key)))))

(reg-event-fx
  :log-in
  set-user-interceptor
  (fn [{:keys [db]} [_ {:keys [email password]}]]
    (let [user              (get-in db [:users email])
          correct-password? (= (get-in user [:profile :password]) password)]
      (cond
        (not user) {:db (assoc-in db [:errors :email] "User not found")}
        (not correct-password?) {:db (assoc-in db [:errors :password] "Wrong password")}
        correct-password? {:db          (-> db
                                            (assoc-in [:auth :uid] (:uid user))
                                            (update-in [:errors] dissoc :email :password))
                           :dispatch    [:active-page :saved]
                           :navigate-to {:path "/saved"}}))))

(reg-event-fx
  :sign-up
  set-user-interceptor
  (fn [{:keys [db]} [_ {:keys [first-name last-name email password]}]]
    {:db          (-> db
                      (assoc-in [:auth :uid] email)
                      (assoc-in [:users email] {:id      email
                                                :profile {:first-name first-name
                                                          :last-name  last-name
                                                          :email      email
                                                          :password   password
                                                          :img        "img/avatar.jpg"}
                                                :saved   #{}
                                                :inboxes {}}))
     :dispatch    [:active-page :saved]
     :navigate-to {:path "/saved"}}))

(reg-event-fx
  :log-out
  remove-user-interceptor
  (fn [{:keys [db]} _]
    {:db          (assoc-in db [:auth :uid] nil)
     :dispatch    [:active-page :recipes]
     :navigate-to {:path "/recipes"}}))

(reg-event-db
  :update-profile
  (fn [db [_ profile]]
    (let [uid (get-in db [:auth :uid])]
      (update-in db [:users uid :profile] merge (select-keys profile [:first-name :last-name])))))

(reg-event-fx
  :delete-profile
  remove-user-interceptor
  (fn [{:keys [db]} _]
    (let [uid (get-in db [:auth :uid])]
      {:db          (-> db
                        (assoc-in [:auth :uid] nil)
                        (update-in [:users] dissoc uid))
       :dispatch    [:active-page :recipes]
       :navigate-to {:path "/recipes"}})))
