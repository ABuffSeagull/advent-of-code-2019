(ns day2.core
  (:use [clojure.string :only [split trim]])
  (:gen-class))

(defn split-comma
  [text]
  (split text #","))

(defn parse-input
  [input]
  (->> input
       trim
       split-comma
       (map #(Integer/parseInt %))
       vec))

(defn change-vector
  [op input position]
  (let [[first-pos second-pos save-pos] (nthnext input (inc position))]
    (assoc input
           save-pos
           (op
            (nth input first-pos)
            (nth input second-pos)))))

(defn compute
  ([input] (compute 0 input))
  ([position input]
   (case (nth input position)
     1 (recur (+ 4 position) (change-vector + input position))
     2 (recur (+ 4 position) (change-vector * input position))
     99 input)))

(defn calc-end
  [[[noun verb]]]
  (+ verb (* noun 100)))

(defn part-2
  [input]
  (->>
   (for [noun (range 100)
         verb (range 100)]
     [[noun verb] (first (compute (assoc input 1 noun 2 verb)))])
   (filter #(= 19690720 (second %)))
   (first)
   calc-end))

(defn part-1
  [input]
  (-> input
      (assoc 1 12
             2 2)
      compute
      first))

(defn -main
  [filename]
  (-> filename
      slurp
      parse-input
      part-2
      println))
