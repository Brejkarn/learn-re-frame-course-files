(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-fx
  :log-in
  (fn [{:keys [db]} [_ {:keys [email password]}]]
    (let [user              (get-in db [:users email])
          correct-password? (= (get-in user [:profile :password]) password)]
      (cond
        (not user) {:db (assoc-in db [:errors :email] "User not found")}
        (not correct-password?) {:db (assoc-in db [:errors :password] "Wrong password")}
        correct-password? {:db       (-> db
                                         (assoc-in [:auth :uid] (:uid user))
                                         (update-in [:errors] dissoc :email :password))
                           :dispatch [:active-nav :saved]}))))

(reg-event-fx
  :sign-up
  (fn [{:keys [db]} [_ {:keys [first-name last-name email password]}]]
    {:db       (-> db
                   (assoc-in [:auth :uid] email)
                   (assoc-in [:users email] {:id      email
                                             :profile {:first-name first-name
                                                       :last-name  last-name
                                                       :email      email
                                                       :password   password
                                                       :img        "img/avatar.jpg"}
                                             :saved   #{}
                                             :inboxes {}}))
     :dispatch [:active-nav :saved]}))

(reg-event-fx
  :log-out
  (fn [{:keys [db]} [_ _]]
    {:db       (assoc-in db [:auth :uid] nil)
     :dispatch [:active-nav :recipes]}))

(reg-event-db
  :update-profile
  (fn [db [_ profile]]
    (let [uid (get-in db [:auth :uid])]
      (update-in db [:users uid :profile] merge (select-keys profile [:first-name :last-name])))))

(reg-event-fx
  :delete-profile
  (fn [{:keys [db]} _]
    (let [uid (get-in db [:auth :uid])]
      {:db       (-> db
                     (assoc-in [:auth :uid] nil)
                     (update-in [:users] dissoc uid))
       :dispatch [:active-nav :recipes]})))
