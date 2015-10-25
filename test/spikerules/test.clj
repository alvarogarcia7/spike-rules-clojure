(ns spikerules.test
  (:use midje.sweet)
  (:use [spikerules.rules :refer :all]))

(fact "check rules' composition"
      ((:not-empty rules-repository) "2") => true
      ((:not-empty rules-repository) "") => false
      ((:empty rules-repository) "") => true
      ((:empty rules-repository) "1") => false
      ((:number rules-repository) " ") => false
      ((:number rules-repository) "2") => true
      )

(fact "check a single rule validity"
      (is-valid? {:url {:value "a"}}) => true
      (is-valid? {:url {:value "a" :is [:not-empty]}}) => true
      (is-valid? {:url {:value ""  :is [:not-empty]}}) => false
      (is-valid? {:url {:value "a" :is [:empty]}}) => false
      )

(fact "check multiple rules validity"
      (is-valid? {:url {:value " " :is [:not-empty :single-space]}})  => true
      (is-valid? {:url {:value "2" :is [:number :single-space]}}) => false
      (is-valid? {:another {:value " " :is [:number :not-empty]}}) => false
      )
