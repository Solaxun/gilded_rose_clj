(ns gilded-rose.gilded
  (:gen-class))

(def items
  "Can't change this.  In the original OO code, list<items> was a property
  of the class which could not be changed, and each item, represented as
  a hash-map here, was an Item class, which also could not be changed.

  We will honor these constraints by not allowing the structure of items to
  be changed."

  [{:name "+5 Dexterity Vest" :sell-in 10 :quality 20}
   {:name "Aged Brie" :sell-in 2 :quality 10}
   {:name "Elixir of the Mongoose" :sell-in 5 :quality 7}
   {:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}
   {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 15 :quality 20}
   {:name "Conjured Mana Cake" :sell-in 3 :quality 6}])

(defmulti update-quality-mm :name)

(defmethod update-quality-mm "Aged Brie"
  [{:keys [name sell-in quality] :as item}]
  (-> item
      (update :sell-in dec)
      (update :quality #(min (if (<= sell-in 0)
                               (+ 2 %)
                               (+ 1 %))
                             50))))

(defmethod update-quality-mm "Backstage passes to a TAFKAL80ETC concert"
  [{:keys [name sell-in quality] :as item}]
  (-> item
      (update :sell-in dec)
      (update :quality #(min (condp >= sell-in
                               0  0
                               5  (+ 3 %)
                               10 (+ 2 %)
                               (+ 1 %))
                             50))))

(defmethod update-quality-mm "+5 Dexterity Vest"
  [{:keys [name sell-in quality] :as item}]
  (-> item
      (update :sell-in dec)
      (update :quality #(max (if (<= sell-in 0)
                               (- % 2)
                               (- % 1))
                             0))))

(defmethod update-quality-mm "Elixir of the Mongoose"
  [{:keys [name sell-in quality] :as item}]
  (-> item
      (update :sell-in dec)
      (update :quality #(max (if (<= sell-in 0)
                               (- % 2)
                               (- % 1))
                             0))))

(defmethod update-quality-mm "Sulfuras, Hand of Ragnaros"
  [{:keys [name sell-in quality] :as item}]
  item)

(defmethod update-quality-mm "Conjured Mana Cake"
  [{:keys [name sell-in quality] :as item}]
  (-> item
      (update :sell-in dec)
      (update :quality #(max (if (<= sell-in 0)
                               (- % 4)
                               (- % 2))
                             0))))

(defn update-quality
  "Your implementatinon here.  Method body can be changed,but not
  the signature.

  Each day reduce both quality and sell-in by the amt required for
  the relevant item."
  [item]
  (update-quality-mm item))

(defn days [item n]
  (nth (iterate update-quality item) n))

(defn elapsed-days [n]
  (mapv #(days % n)
        items))
