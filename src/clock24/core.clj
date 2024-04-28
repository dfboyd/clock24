(ns clock24.core
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:gen-class))

;; I think we'll start with a simple minute hand clock

(def tau 6.28318530)
(def hour (/ tau 24))
(def twohour (* hour 2))
(def halfhour (/ hour 2))
(def minute (/ tau 1440))

(def onesecond (/ tau 60))

(defn setup
  ([]
   ;; setup fn with no args returns initial state
   (let [dt (java.time.LocalDateTime/now)]
     {:angle (+
               (/ tau 4)
               (* hour (.getHour dt))
               (* minute (.getMinute dt)))
      :second (+ 
                (/ tau 4) 
                (* onesecond (.getSecond dt)))
      :minute_frac (/ (.getMinute dt) 60)}))
  ([state]
   ;; with one arg, it's an update; but we just call
   ;; the same fn
   (setup)))

(defn xy-from-polar [r theta]
  [(* r (q/cos theta))
   (* r (q/sin theta))])

(defn tic [r1 r2 theta]
  (let [x1 (* r1 (q/cos theta))
        y1 (* r1 (q/sin theta))
        x2 (* r2 (q/cos theta))
        y2 (* r2 (q/sin theta))]
    (q/line x1 y1 x2 y2)))

(defn draw-state [state]
  (q/frame-rate 1)
  ; (q/window-move (- (q/display-width) 150) 84)

  (q/background 255)
  (let [radius (* 0.9 (/ (q/width) 2))
          hand (* 0.9 radius)]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (q/stroke 0 0 0)
      (q/stroke-weight 2)
      #_ (q/ellipse 0 0 (* 2 radius) (* 2 radius))
      (doseq [h (range 12)]
        (let [even_hour_angle (* twohour h)
              odd_hour_angle (+ hour even_hour_angle)]
          (tic (* radius 0.9) radius even_hour_angle)
          (tic (* radius 0.95) radius odd_hour_angle)
          #_(tic (* radius 0.975) radius (+ halfhour odd_hour_angle))
          #_(tic (* radius 0.975) radius (+ halfhour even_hour_angle))))
      (q/stroke-weight 3)
      (q/stroke 230 0 0)
      (tic 0 hand (:angle state))
      (q/fill 230 0 0)
      (let [[x y] (xy-from-polar radius (:second state))]
        (q/ellipse x y 5 5))
      )
    (q/stroke 0 0 0)
    (q/stroke-weight 2)
    (let [minute_position (* (q/height) (:minute_frac state))]
      (q/line minute_position 0 minute_position (q/height)))))

(defn -main [& args]
  (q/sketch :title "clock24"
            :size [150 150]
            :setup setup
            :update setup
            :draw draw-state
            :middleware [m/fun-mode]
            :features [:exit-on-close]))
