(ns gilded-rose.gilded-test
  (:require [clojure.test :refer :all]
            [gilded-rose.gilded :refer :all]))

(deftest legendary
  (let [lgnd {:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}
        daily20 (take 20 (iterate update-quality lgnd))]
    (every? (partial = lgnd) daily20)))

(deftest conjured
  (let [c {:name "Conjured Mana Cake" :sell-in 5 :quality 15}]
    (are [c-static c-calc] (= c-static c-calc)
      {:name "Conjured Mana Cake" :sell-in 5 :quality 15} (days c 0)
      {:name "Conjured Mana Cake" :sell-in 0 :quality 5} (days c 5)
      {:name "Conjured Mana Cake" :sell-in -1 :quality 1} (days c 6)
      {:name "Conjured Mana Cake" :sell-in -3 :quality 0} (days c 8)
      {:name "Conjured Mana Cake" :sell-in -95 :quality 0} (days c 100))))

(deftest normal
  (let [e {:name "Elixir of the Mongoose" :sell-in 5 :quality 10}]
    (are [e-static e-calc] (= e-static e-calc)
      {:name "Elixir of the Mongoose" :sell-in 5 :quality 10} (days e 0)
      {:name "Elixir of the Mongoose" :sell-in 4 :quality 9} (days e 1)
      {:name "Elixir of the Mongoose" :sell-in 0 :quality 5} (days e 5)
      {:name "Elixir of the Mongoose" :sell-in -2 :quality 1} (days e 7)
      {:name "Elixir of the Mongoose" :sell-in -95 :quality 0} (days e 100))))

(deftest tickets
  (let [t {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 15 :quality 20}]
    (are [t-static t-calc] (= t-static t-calc)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 15 :quality 20} (days t 0)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 14 :quality 21} (days t 1)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 10 :quality 25} (days t 5)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 7 :quality 31} (days t 8)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 3 :quality 41} (days t 12)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 1 :quality 47} (days t 14)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 0 :quality 50} (days t 15)
      {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in -85 :quality 0} (days t 100))))

(deftest aged
  (let [b {:name "Aged Brie" :sell-in 2 :quality 10}]
    (are [b-static b-calc] (= b-static b-calc)
      {:name "Aged Brie" :sell-in 2 :quality 10} (days b 0)
      {:name "Aged Brie" :sell-in 1 :quality 11} (days b 1)
      {:name "Aged Brie" :sell-in 0 :quality 12} (days b 2)
      {:name "Aged Brie" :sell-in -3 :quality 18} (days b 5)
      {:name "Aged Brie" :sell-in -98 :quality 50} (days b 100))))
