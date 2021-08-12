(ns nubank-alura-onboarding.models.cliente
  (:require [schema.core :as s]
            [nubank-alura-onboarding.models.common :as m-common]))

(s/def Cliente
  {:nome  s/Str
   :cpf   s/Str
   :email s/Str})

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
  [nome :- s/Str, cpf :- s/Str, email :- s/Str]
  {:nome nome
   :cpf cpf
   :email email})

(let [cliente (cria-novo-cliente "Mateus" "0000.0000.0000-00" "teste@nubank.com.br")]
  (println "Teste cria cliente:")
  (println "Teste schema:" (s/validate Cliente cliente))
  (map println cliente))