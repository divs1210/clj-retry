(ns clj-retry.core-test
  (:require [clojure.test :refer [deftest is]]
            [clj-retry.core :refer [with-retry]]))

(deftest with-retry-test
  (with-retry [a 1]
    (if (= a 1)
      (throw (Exception.))
      (is (= a 2)))
    (catch Exception _
      (retry 2))))
