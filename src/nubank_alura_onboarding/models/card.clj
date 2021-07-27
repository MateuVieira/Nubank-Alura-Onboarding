(ns nubank-alura-onboarding.models.card)

(defn cria-novo-cartao
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
  [numero, cvv, validade, limite]
  {:numero numero
   :cvv cvv
   :validade validade
   :limite limite})

;(println "Teste cartão de crédito:")
;(map println (cria-novo-cartao "1111-1111-1111-1111", 001, "01/21", 4000))