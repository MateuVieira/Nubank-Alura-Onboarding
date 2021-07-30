(ns nubank-alura-onboarding.core
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]))

;(println "Teste - Cliente:\n" (model-cliente/cria-novo-cliente "Mateus", "0000.0000.0000-00", "teste@nubank.com.br"))
;(println "\nTeste - Catão de Crédito:\n" (model-card/cria-novo-cartao "1111-1111-1111-1111", 011, "01/22", 4000))
;(println "\nTeste - Catão de Crédito:\n" (model-compra/cria-nova-compra "29/07", 100, "loja", "saude"))


; MAIN
(let [lista-de-compras (model-compra/cria-mock-lista-de-compras 10)]
  (model-compra/print-lista-de-compras lista-de-compras)
  (model-compra/total-por-categoria lista-de-compras)
  (model-compra/calculo-de-fatura-do-mes lista-de-compras "11")
  (print "\nTeste filtrar GPA:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :estabelecimento, "GPA", lista-de-compras))
  (print "\nTeste filtrar Valor:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :valor, 100, lista-de-compras))
  (print "\nTeste filtro Data:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :data, "2021-07-29", lista-de-compras))
  (print "\nTeste filtro Categoria:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :categoria, "Saúde", lista-de-compras))
  (print "\nTeste novo Filtro para valores:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :valor, 100, 300, lista-de-compras))
  (print "\nTeste novo Filtro para datas:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :data, "2021-07-01", "2021-07-30", lista-de-compras)))


