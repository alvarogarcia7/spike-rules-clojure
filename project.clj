(defproject spike-rules "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot spikerules.rules
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
	     :dev {
                     :dependencies [[midje "1.6.3"]]
                     :plugins      [[lein-midje "3.1.3"]]
               }})
