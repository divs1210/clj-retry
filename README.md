# clj-retry

[clj-rery is this gist in library form](https://gist.github.com/aphyr/a81825ab80656679db78)

It's really inconvenient not being able to recur from within `catch` forms.

This library provides `with-retry` as an alternative to `try`, which acts as a recusrion point.

## Usage

```clojure
(require '[clj-retry.core :refer [with-retry]])

(with-retry [i 0]
  (if (< i 5)
    (throw (Exception.))
    (println "done"))
  (catch Exception _
    (println i)
    (retry (inc i))))

;; this code prints:

0
1
2
3
4
done
```

## License

Copyright © 2022 Divyansh Prakash

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
