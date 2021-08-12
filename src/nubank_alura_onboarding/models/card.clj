(ns nubank-alura-onboarding.models.card
  (:require [schema.core :as s]
            [nubank-alura-onboarding.models.common :as m-common]))

(s/def Cartao {:cartao/id                        java.util.UUID
               (s/optional-key :cartao/numero)   s/Str
               (s/optional-key :cartao/cvv)      s/Int
               (s/optional-key :cartao/validade) s/Str
               (s/optional-key :cartao/limite)   s/Num})

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
  ([numero :- s/Str, cvv :- s/Int, validade :- s/Str, limite :- s/Num]
   (cria-novo-cartao (m-common/cria-uuid) numero cvv validade limite))
  ([uuid :- java.util.UUID
    numero :- s/Str
    cvv :- s/Int
    validade :- s/Str
    limite :- s/Num]
   {:cartao/id       uuid
    :cartao/numero   numero
    :cartao/cvv      cvv
    :cartao/validade validade
    :cartao/limite   limite}))

(s/defn gera-um-cartao :- Cartao
  []
  (cria-novo-cartao "1111-1111-1111-1111", 001, "01/21", 4000M))

(let [cartao (cria-novo-cartao "1111-1111-1111-1111", 001, "01/21", 4000M)]
  (println "Teste cartão de crédito:")
  (println "Teste schema:" (s/validate Cartao cartao))
  (map println cartao))