(ns spikerules.test
  (:use midje.sweet))

;http://stackoverflow.com/questions/5621279/in-clojure-how-can-i-convert-a-string-to-a-number
(defn- string->integer [s]
  (when-let [d (re-find #"-?\d+" s)] (Integer. d)))


(def rules-repository
  {:not-empty (comp not empty?)
   :empty     empty?
   :single-space (partial = " ")
   :number       (fn [number] (not (nil? (string->integer number))))}
  )

(defn is-valid? [rules]
  (let [current-rule (:url rules)
        rules2 (:is current-rule)
        current-value (:value current-rule)]
    (cond
      (empty? rules2) true
      :else
      (not-any? #(false? %)
                      (map #((% rules-repository) current-value) rules2)))))


(fact "check rules' composition"
      ((:not-empty rules-repository) "2") => true
      ((:not-empty rules-repository) "") => false
      ((:empty rules-repository) "") => true
      ((:empty rules-repository) "1") => false
      ((:number rules-repository) " ") => false
      ((:number rules-repository) "2") => true
      )

(fact "check rules' validity"
      (is-valid? {:url {:value "a"}}) => true
      (is-valid? {:url {:value "a" :is [:not-empty]}}) => true
      (is-valid? {:url {:value "" :is [:not-empty]}}) => false
      (is-valid? {:url {:value "a" :is [:empty]}}) => false
      (is-valid? {:url {:value " " :is [:not-empty :single-space]}}) => true
      (is-valid? {:url {:value "2" :is [:number :single-space]}}) => false
      )

