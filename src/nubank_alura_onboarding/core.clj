(ns nubank-alura-onboarding.core
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

;(println "Teste - Cliente:\n" (model-cliente/cria-novo-cliente "Mateus", "0000.0000.0000-00", "teste@nubank.com.br"))
;(println "\nTeste - Catão de Crédito:\n" (model-card/cria-novo-cartao "1111-1111-1111-1111", 011, "01/22", 4000))
;(println "\nTeste - Catão de Crédito:\n" (model-compra/cria-nova-compra "29/07", 100, "loja", "saude"))

(defn calc-total
  [data]
  (reduce + data))

(defn calc-total-por-categoria
  [[categoria compras]]
  (let [valor-das-compras (map :valor compras)
        total (calc-total valor-das-compras)]
    {:categoria categoria, :total total}))

(defn print-total-por-categoria
  [data]
  (pp/print-table [:categoria :total] data))

(defn total-por-categoria
  [lista-de-compra]
  (print "\nCalculo do valor total comprado organizado por categoria:")
  (print-total-por-categoria (->>
             lista-de-compra
             (group-by :categoria)
             (map calc-total-por-categoria))))

(defn pega-mes?
  [compra mes]
  (let [data (get compra :data)
        data-split (str/split data #"/")
        mes-compra (get data-split 1)]
    (= mes-compra mes)))

(defn filtra-por-mes
  [mes]
  (fn [lista-de-compras] (pega-mes? lista-de-compras mes)))

(defn calculo-de-fatura-do-mes
  [lista-de-compras mes]
  (let [filter-by-mes (filter (filtra-por-mes mes) lista-de-compras)
        valor-das-compras (map :valor filter-by-mes)
        total (calc-total valor-das-compras )]
    (println "\nValor total da Fatura do mês" mes "\n->" total)))

; MAIN
(let [lista-de-compras (model-compra/cria-mock-lista-de-compras 100)]
  (model-compra/print-lista-de-compras lista-de-compras)
  (total-por-categoria lista-de-compras)
  (calculo-de-fatura-do-mes lista-de-compras "11"))
