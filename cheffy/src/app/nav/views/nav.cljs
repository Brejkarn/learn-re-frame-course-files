(ns app.nav.views.nav
  (:require [app.nav.views.auth :refer [authenticated]]
            [app.nav.views.public :refer [public]]))

(defn nav
  []
  (let [user false]
    (if user
      [authenticated]
      [public])))