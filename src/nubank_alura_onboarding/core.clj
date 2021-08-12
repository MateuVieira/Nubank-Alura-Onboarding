(ns nubank-alura-onboarding.core
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]
            [schema.core :as s]))

(s/set-compile-fn-validation! true)

; MAIN
(let [lista-de-compras (model-compra/cria-mock-lista-de-compras 100)]
  (model-compra/print-lista-de-compras lista-de-compras)
  (model-compra/total-por-categoria lista-de-compras)
  (model-compra/calculo-de-fatura-do-mes lista-de-compras "11")
  (print "\nTeste filtrar GPA:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/estabelecimento, "GPA", lista-de-compras))
  (print "\nTeste filtrar Valor:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/valor, 100, lista-de-compras))
  (print "\nTeste filtro Data:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/data, "2021-07-29", lista-de-compras))
  (print "\nTeste filtro Categoria:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/categoria, "Saúde", lista-de-compras))
  (print "\nTeste novo Filtro para valores:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/valor, 100, 300, lista-de-compras))
  (print "\nTeste novo Filtro para datas:")
  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/data, "2021-07-01", "2021-07-30", lista-de-compras))
  (println  ""))
