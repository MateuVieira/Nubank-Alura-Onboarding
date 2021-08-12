(ns nubank-alura-onboarding.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [schema.core :as s]
            [clojure.walk :as walk]
            [nubank-alura-onboarding.models.compra :as m-compra]
            [nubank-alura-onboarding.models.cliente :as m-cliente]
            [nubank-alura-onboarding.models.card :as m-card]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn abre-conexao! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco! []
  (d/delete-database db-uri))

(def schema [; Cliente
             {:db/ident       :cliente/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              }
             {:db/ident       :cliente/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cliente/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cliente/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cliente/cartao
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one
              }

             ; Compra
             {:db/ident       :compra/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              }
             {:db/ident       :compra/data
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :compra/valor
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :compra/estabelecimento
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :compra/categoria
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :compra/cartao
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one
              }

             ; Cartão
             {:db/ident       :cartao/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              }
             {:db/ident       :cartao/numero
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cartao/cvv
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cartao/validade
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              }
             {:db/ident       :cartao/limite
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              }
             ])

(defn cria-schema!
  [conexao]
  (d/transact conexao schema))











