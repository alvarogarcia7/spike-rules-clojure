(ns spikerules.rules)

;http://stackoverflow.com/questions/5621279/in-clojure-how-can-i-convert-a-string-to-a-number
(defn- string->integer [s]
  (when-let [d (re-find #"-?\d+" s)] (Integer. d)))


(def rules-repository
  {:not-empty    (comp not empty?)
   :empty        empty?
   :single-space (partial = " ")
   :number       (comp not nil? string->integer)}
  )

(defn- all-true? [coll]
  (not-any? #(false? %) coll))

(defn- validate-single-rule [rule]
  (let [current-rule rule
        rules2 (:is current-rule)
        current-value (:value current-rule)]
    (cond
      (empty? rules2) true
      :else
      (all-true?
        (map #((% rules-repository) current-value) rules2)))))

(defn is-valid? [rules]
  (all-true? (map #(validate-single-rule (second %)) (seq rules))))