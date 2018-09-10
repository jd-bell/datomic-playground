(defproject datomic-playground "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
  				 [org.clojure/spec.alpha "0.1.143"]
                 [org.clojure/core.specs.alpha "0.1.10"]
                 [com.datomic/client-cloud "0.8.63"]]
  :main ^:skip-aot datomic-playground.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
