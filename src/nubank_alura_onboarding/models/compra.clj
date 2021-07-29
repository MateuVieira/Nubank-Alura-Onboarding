(ns nubank-alura-onboarding.models.compra
  (:require [clojure.pprint :as pp]
            [clojure.string :as str]
            [java-time :as jt]))


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
  {:data            data
   :valor           valor
   :estabelecimento estabelecimento
   :categoria       categoria})

;(println "Teste campra:")
;(map println (cria-nova-compra "29/07", 100, "loja", "saude"))

(defn rand-numero
  "Retorna um número inteiro entre 1 e o valor limite"
  [limite]
  (->>
    limite
    dec
    rand-int
    inc))

(defn rand-data-de-validade
  "Retorna um data formada por dia e mês no formato:
  dd/mm"
  []
  (try
    (let [dia (rand-numero 30)
          mes (rand-numero 12)
          date (jt/local-date 2021 mes dia)]
      (str date))
    (catch Exception e (rand-data-de-validade))))

;(println (rand-data-de-validade))

(defn rand-valor-de-100
  "Retorna o mock de um valor monetario multiplo de 100
  com um range de 100 a 1000"
  []
  (let [fator (rand-numero 10)
        valor (* fator 100)]
    valor))

;(println (rand-valor-de-100))

(def lista-de-estabelecimentos ["Americanas"
                                "GPA"
                                "Via Varejo"
                                "Netshoes"
                                "Magalu"
                                "B2W"])

(def lista-de-categorias ["Saúde"
                          "Alimentação"
                          "Eletrônicos"
                          "Educação"
                          "Livros"
                          "Games"])

(defn rand-seq-get-item
  "Retorna o valor de um dos elementos de uma sequencia"
  [sequencia]
  (let [numero-de-itens (count sequencia)
        index (rand-int numero-de-itens)
        valor (get sequencia index)]
    valor))

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
       (let [proximo-numero (dec numero)
             data-de-validade (rand-data-de-validade)
             valor (rand-valor-de-100)
             loja (rand-seq-get-item lista-de-estabelecimentos)
             categoria (rand-seq-get-item lista-de-categorias)
             compra (cria-nova-compra data-de-validade, valor, loja, categoria)
             nova-lista-de-compras (conj lista-de-compras compra)]
         (recur proximo-numero nova-lista-de-compras)))
     (sort-by :data lista-de-compras))))

(defn print-lista-de-compras
  "Esta função realiza o print de umalista de compras no formato
  de uma tabela"
  [lista-de-compras]
  (let [primeiro-elemento (first lista-de-compras)
        chaves (keys primeiro-elemento)]
    (pp/print-table chaves lista-de-compras)))

(let [lista-de-compras (cria-mock-lista-de-compras 10)]
  (print-lista-de-compras lista-de-compras))

