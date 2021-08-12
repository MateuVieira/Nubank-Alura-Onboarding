(ns nubank-alura-onboarding.models.cliente
  (:require [schema.core :as s]
            [nubank-alura-onboarding.models.common :as m-common]))

(s/def Cliente
  {:cliente/id    java.util.UUID
   :cliente/nome  s/Str
   :cliente/cpf   s/Str
   :cliente/email s/Str})

(s/defn cria-novo-cliente :- Cliente
  "Input:
    Nome   =>   String
    CPf    =>   String
    Email  =>   String

   Output:
   {
    :nome
    :cpf
    :email
   }"
  ([nome :- s/Str, cpf :- s/Str, email :- s/Str]
   (cria-novo-cliente (m-common/cria-uuid), nome, cpf, email))
  ([uuid :- java.util.UUID
    nome :- s/Str
    cpf :- s/Str
    email :- s/Str]
   {:cliente/id    uuid
    :cliente/nome  nome
    :cliente/cpf   cpf
    :cliente/email email}))

(let [cliente (cria-novo-cliente "Mateus" "0000.0000.0000-00" "teste@nubank.com.br")]
  (println "Teste cria cliente:")
  (println "Teste schema:" (s/validate Cliente cliente))
  (map println cliente))