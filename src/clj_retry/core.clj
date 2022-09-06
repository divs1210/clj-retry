(ns clj-retry.core
  "Taken from https://gist.github.com/aphyr/a81825ab80656679db78"
  (:require [clojure.walk :as walk]))

(defrecord Retry [bindings])

(defmacro with-retry
  "Like `try`, but with the ability to recur from within (`catch` ...) expressions.
  Provides a (`retry` & new-bindings) form which is usable within `catch` blocks."
  [initial-bindings & body]
  (assert (vector? initial-bindings))
  (assert (even? (count initial-bindings)))
  (let [bindings-count (/ (count initial-bindings) 2)
        body (walk/prewalk (fn [form]
                             (if (and (list? form)
                                      (= 'retry (first form)))
                               (do (assert (= bindings-count
                                              (count (rest form))))
                                   `(Retry. [~@(rest form)]))
                               form))
                           body)
        retval (gensym 'retval)]
    `(loop [~@initial-bindings]
       (let [~retval (try ~@body)]
        (if (instance? Retry ~retval)
          (recur ~@(->> (range bindings-count)
                        (map (fn [i] `(nth (.bindings ~retval) ~i)))))
          ~retval)))))
