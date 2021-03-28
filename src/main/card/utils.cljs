(ns card.utils
  (:require [clojure.string :as str]))


;; Utils  
(defn sum [coll]  (reduce + coll)) 

(defn ** [x n] (reduce * (repeat n x)))

(defn runif [a b]
  "return random integer in range [a, b)"
  (long (Math/floor (+ a (* (- b a) (rand))))))

(defn fmt [coll]
(str/join " "
        (for [quad (partition 4 coll)]
             (str/join quad))))

; luhn's algorithm 
(defn double-other [coll]
  "double every other digit"
  (map-indexed 
    #(if (even? %1) (* 2 %2) %2) coll))

(defn to-digits [number]
  "transofom a number to list of digits"
  (map long (->> number str seq)))

(defn trim [coll]
  (map #(if (> % 9) (- % 9) %) coll))

(defn luhn-digit [digits]
  "get luhn digit using Luhn's Algorithm"
  ;(rem (* 9 (sum (trim (double-other (to-digits number))))) 10)
  (rem (* 9 (sum (trim (double-other digits)))) 10))

(defn master []
  (conj (repeatedly 14 #(rand-int 10)) (runif 1 6) 5))
(defn visa []
  (conj (repeatedly 15 #(rand-int 10)) 4))


(def issuer {:visa visa :master master })

(defn generate-card-number 
  ;([number] (+ (* 10 number) (luhn-digit number)))
  ([digits] (conj (into [] digits) (luhn-digit digits))))

(defn generate-cvc 
  "cvc random 3 digit for non-amex card"
  ([] (runif 100 999))
  ([amex] (runif 1000 9999)))

; done
(defn generate-exp-date []
  (let [year (runif 22 25)
        m (runif 1 12)
        month (if (< m 10) (str "0" m) m)]
    (str month "/" year)))

(defn generate-card [card]
  {:card-number (generate-card-number (card))
   :cvc (generate-cvc)
   :exp-date (generate-exp-date)})


(defn rand-name []
  (rand-nth [
"August Myers"
"Avery Ware"
"Blake Nichols"
"Charlie Adams "
"Eden Cooper"
"Elliot Beswick"
"Elliott Fogg"
"Emerson Dickson"
"Finley Brown "
"Jordan Myers"
"Karter Johnson"
"Parker Hopkins"
"Peyton Sharp"
"Quincy Hill"
"Riley Jones"
"River Livingston"
"Rowan Cooper"
"Ryan Allman"
"Taylor Bailey"
]) )


