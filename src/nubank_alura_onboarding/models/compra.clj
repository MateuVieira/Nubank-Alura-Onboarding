(ns nubank-alura-onboarding.models.compra)

(defn cria-nova-compra
  "Input:
    Data             =>   String
    Valor            =>   Int
    Estabelecimento  =>   String
    Categoria        =>   String

   Output:
   {
    :data
    :valor
    :estabelecimento
    :categoria
   }"
  [data, valor, estabelecimento, categoria]
  {:data data
   :valor valor
   :estabelecimento estabelecimento
   :categoria categoria})

;(println "Teste campra:")
;(map println (cria-nova-compra "29/07", 100, "loja", "saude"))