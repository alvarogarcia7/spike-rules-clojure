(ns spikerules.test
  (:use midje.sweet))

(def rules-repository
  {:not-empty (comp not empty?)
   :empty empty?
   })

(defn is-valid? [rules]
  (let [current-rule (:url rules)
        rules2 (:is current-rule)]
    (cond
      (empty? rules2) true
      :else (((first rules2) rules-repository) (:value current-rule)))))

(fact "check rules' validity"
      (is-valid? {:url {:value "a"}}) => true
      (is-valid? {:url {:value "a" :is [:not-empty]}}) => true
      (is-valid? {:url {:value "" :is [:not-empty]}}) => false
      (is-valid? {:url {:value "a" :is [:empty]}}) => false
      (is-valid? {:url {:value "" :is [:empty]}}) => true
      )

