(ns nubank-alura-onboarding.models.card
  (:require [schema.core :as s]
            [nubank-alura-onboarding.models.common :as m-common]))

(s/def Cartao {:card/id       java.util.UUID
               :card/numero   s/Str
               :card/cvv      s/Int
               :card/validade s/Str
               :card/limite   s/Num})

(s/defn cria-novo-cartao :- Cartao
  "Input:
    Number    =>   String
    CVV       =>   Int
    Validade  =>   String
    Limite    =>   Int

   Output:
   {
    :numero
    :cvv
    :validade
    :limite
   }"
  [numero :- s/Str, cvv :- s/Int, validade :- s/Str, limite :- s/Num]
  {:card/numero   numero
   :card/cvv      cvv
   :card/validade validade
   :card/limite   limite})

(let [cartao (cria-novo-cartao "1111-1111-1111-1111", 001, "01/21", 4000)]
  (println "Teste cartão de crédito:")
  (println "Teste schema:" (s/validate Cartao cartao))
  (map println cartao))