(ns nubank-alura-onboarding.core
  (:require [nubank-alura-onboarding.models.cliente :as model-cliente]
            [nubank-alura-onboarding.models.card :as model-card]
            [nubank-alura-onboarding.models.compra :as model-compra]))

;(println "Teste - Cliente:\n" (model-cliente/cria-novo-cliente "Mateus", "0000.0000.0000-00", "teste@nubank.com.br"))
;(println "\nTeste - Catão de Crédito:\n" (model-card/cria-novo-cartao "1111-1111-1111-1111", 011, "01/22", 4000))
;(println "\nTeste - Catão de Crédito:\n" (model-compra/cria-nova-compra "29/07", 100, "loja", "saude"))

(defn calc-total-por-categoria
  [[categoria compras]]
  (->>
    compras
    (map :valor)
    (reduce +)
    (println "Categoria:" categoria "- Valor Total:")))

(defn total-por-categoria
  [lista-de-compra]
  (->>
    lista-de-compra
    (group-by :categoria)
    (map calc-total-por-categoria)))

(let [lista-de-compra [(model-compra/cria-nova-compra "29/07", 100, "loja", "saude")
                    (model-compra/cria-nova-compra "29/07", 100, "loja", "saude")
                    (model-compra/cria-nova-compra "29/07", 100, "loja", "alimentacao")
                    (model-compra/cria-nova-compra "29/07", 100, "loja", "alimentacao")
                    (model-compra/cria-nova-compra "29/07", 100, "loja", "alimentacao")
                    (model-compra/cria-nova-compra "29/07", 100, "loja", "roupa")]]
  (println "Lista de compras" lista-de-compra)
  (total-por-categoria lista-de-compra))



