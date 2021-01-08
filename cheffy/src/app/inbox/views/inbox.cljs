(ns app.inbox.views.inbox
  (:require [app.components.page-nav :refer [page-nav]]))

(defn inbox
  []
  [page-nav {:center "inbox"}])
