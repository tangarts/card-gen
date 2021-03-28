(ns card.views
    (:require
      [reagent.core :as r]
      [card.utils :as utils]))

(def state (r/atom (utils/generate-card utils/master)))
(def logo (r/atom "img/mastercard-logo.svg"))
(defonce card-holder (utils/rand-name))


(defn card-number []
  (fn []
    [:div {:class "flex justify-center mt-8 mb-2 px-4"}
     [:span {
      :class "text-center text-white text-base sm:text-lg font-bold"
      :style {:letter-spacing "0.3em"}}
      (utils/fmt (:card-number @state))]]))

(defn card []
  (fn []
      [:div {:class "max-w-sm mx-auto bg-gray-700 rounded-lg h-56"}
       [:div {:class " p-2"}
         [:div {:class "p-2 flex mt-2 justify-end text-white"}
          [:span {:class "text-2xl sm:text-3xl font-bold"} "CLJS" ]]
         [card-number]
         [:div {:class "flex flex-row"}
          [:div {:class "p-2 flex flex-col w-1/2"}
           [:div {:class "flex items-center text-white"}
            [:div {:class "flex flex-col"}
             [:span {:class "font-bold text-gray-300 text-xs sm:text-sm"} "CVC"]
             [:span {:class "font-bold text-xs sm:text-sm"} (:cvc @state)] " "]
            [:div {:class "flex flex-col px-4"}
             [:span {:class "font-bold text-gray-300 text-xs sm:text-sm"} "EXP"]
             [:span {:class "font-bold text-xs sm:text-sm"} (:exp-date @state)] " "]] 
           [:div {:class "flex"}
            [:span {:class "font-bold text-md"} card-holder] " "]] 
          ; logo right side
          [:div {:class "p-2 flex flex-col w-1/2"}
           [:div {:class "flex mt-6 justify-end text-white "}
            [:img {:src @logo :class "w-12 sm:w-16 "}]]]]]
       ]))

(defn buttons []
  [:div {:class "flex flex-row justify-center pt-8"}
    [:input
     {:type "image"
     :src "img/mastercard-logo.svg"
     :class "w-12 sm:w-16 px-2 border-opacity-0"
     :on-click #((reset! state (utils/generate-card utils/master))
                 (reset!  logo "img/mastercard-logo.svg")
                 )}]
   [:input
     {:type "image"
     :src "img/visa-logo.svg"
     :class "w-12 sm:w-16 px-2 border-opacity-0"
     :on-click #((reset! state (utils/generate-card utils/visa))
                 (reset! logo "img/visa-logo.svg")
                 )}]
    ])


(defn footer []
  [:footer
   {:style {:bottom 0 :position "absolute" :text-align "center" :width "100%"}} 
   [:p "made by "
    [:a {:href "https://tangarts.github.io/about"} "tangarts"] ]])

(defn app []
  [:div {:class "w-full h-full py-20 min-h-screen bg-gray-300
                 flex flex-col" }
   [card]
   [buttons]
   [footer]])

