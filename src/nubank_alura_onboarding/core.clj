(ns nubank-alura-onboarding.core
  (:use clojure.pprint)
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]
            [nubank-alura-onboarding.db :as db]
            [schema.core :as s]
            [datomic.api :as d]))

(s/set-compile-fn-validation! true)

; MAIN
;(let [lista-de-compras (model-compra/cria-mock-lista-de-compras 10)]
;  (model-compra/print-lista-de-compras lista-de-compras)
;  (model-compra/total-por-categoria lista-de-compras)
;  (model-compra/calculo-de-fatura-do-mes lista-de-compras "11")
;  (print "\nTeste filtrar GPA:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/estabelecimento, "GPA", lista-de-compras))
;  (print "\nTeste filtrar Valor:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/valor, 100, lista-de-compras))
;  (print "\nTeste filtro Data:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/data, "2021-07-29", lista-de-compras))
;  (print "\nTeste filtro Categoria:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/categoria, "Saúde", lista-de-compras))
;  (print "\nTeste novo Filtro para valores:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/valor, 100, 300, lista-de-compras))
;  (print "\nTeste novo Filtro para datas:")
;  (model-compra/print-lista-de-compras (model-compra/filtrar-por :compra/data, "2021-07-01", "2021-07-30", lista-de-compras))
;  (println  ""))

(db/apaga-banco!)
(def conn (db/abre-conexao!))

(db/cria-schema! conn)

(defn todos-os-cliente
  [db]
  (d/q '[:find (pull ?e [* {:cliente/cartao [*]}])
         ;?e ?cliente ?id
         ;
         :where [?e :cliente/nome ?cliente]]
       db))

(defn todos-as-compras
  [db]
  (d/q '[:find (pull ?e [* {:compra/cartao [*]}])
         ;?e ?cliente ?id
         ;
         :where [?e :compra/id ?cliente]]
       db))

(s/defn adiciona-ou-altera-cliente!
  [conn, produtos]
   (d/transact conn produtos))

(let [cliente1 (model-cliente/cria-novo-cliente "Mateus", "39656320867", "mateus@nubank.com.br")
      cliente2 (model-cliente/cria-novo-cliente "Thiago", "00000000000", "thiago@nubank.com.br")
      id-cartao-cliente1 (get cliente1 :cliente/cartao)
      id-cartao-cliente2 (get cliente2 :cliente/cartao)
      compra1 (model-compra/cria-nova-compra "01/20", 100M, "Teste1", "Categoria1", id-cartao-cliente1)
      compra2 (model-compra/cria-nova-compra "02/20", 100M, "Teste2", "Categoria2", id-cartao-cliente2)
      ]
  (adiciona-ou-altera-cliente! conn [cliente2, cliente1])
  ;(println "Teste id-cartao-cliente1:" id-cartao-cliente1)
  ;(println "Teste:" compra1 "\n" compra2)
  (d/transact conn [compra1, compra2])
  )

(println "Query:" (todos-os-cliente (d/db conn)))

(todos-as-compras (d/db conn))

















