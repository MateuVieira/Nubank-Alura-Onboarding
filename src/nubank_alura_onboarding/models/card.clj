(ns nubank-alura-onboarding.models.card
  (:require [schema.core :as s]
            [nubank-alura-onboarding.models.common :as m-common]))

(s/def Cartao {:card/id                        java.util.UUID
               (s/optional-key :card/numero)   s/Str
               (s/optional-key :card/cvv)      s/Int
               (s/optional-key :card/validade) s/Str
               (s/optional-key :card/limite)   s/Num})

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
   {:card/id       uuid
    :card/numero   numero
    :card/cvv      cvv
    :card/validade validade
    :card/limite   limite}))

(let [cartao (cria-novo-cartao "1111-1111-1111-1111", 001, "01/21", 4000)]
  (println "Teste cartão de crédito:")
  (println "Teste schema:" (s/validate Cartao cartao))
  (map println cartao))