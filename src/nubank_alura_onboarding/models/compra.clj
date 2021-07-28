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

(defn cria-mock-lista-de-compras
  "Esta função irá criar um mock de uma lista de compras
  com um número n de transações.

  n => Int"
  ([numero]
   (cria-mock-lista-de-compras numero []))
  ([numero lista-de-compras]
   (if (not (< numero 0))
     (do
       ;(println "Numero:" numero "Count:" (count lista-de-compras) "Lista de compras:" lista-de-compras )
       (recur (dec numero) (conj lista-de-compras (cria-nova-compra "29/07", 100, "loja", "saude"))))
     lista-de-compras)
   ))

(println (cria-mock-lista-de-compras 10))