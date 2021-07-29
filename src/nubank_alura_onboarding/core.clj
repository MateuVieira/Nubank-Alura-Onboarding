(ns nubank-alura-onboarding.core
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [java-time :as jt]))

;(println "Teste - Cliente:\n" (model-cliente/cria-novo-cliente "Mateus", "0000.0000.0000-00", "teste@nubank.com.br"))
;(println "\nTeste - Catão de Crédito:\n" (model-card/cria-novo-cartao "1111-1111-1111-1111", 011, "01/22", 4000))
;(println "\nTeste - Catão de Crédito:\n" (model-compra/cria-nova-compra "29/07", 100, "loja", "saude"))


; MAIN
(let [lista-de-compras (model-compra/cria-mock-lista-de-compras 1000)]
  (model-compra/print-lista-de-compras lista-de-compras)
  (model-compra/total-por-categoria lista-de-compras)
  (model-compra/calculo-de-fatura-do-mes lista-de-compras "11")
  (println "\nTeste filtrar GPA:" (model-compra/filtrar-por :estabelecimento, "GPA", lista-de-compras))
  (println "\nTeste filtrar Valor:" (model-compra/filtrar-por :valor, 100, lista-de-compras))
  (println "\nTeste filtro Data:" (model-compra/filtrar-por :data, "2021-07-29", lista-de-compras))
  (println "\nTeste filtro Categoria:" (model-compra/filtrar-por :categoria, "Saúde", lista-de-compras)))


