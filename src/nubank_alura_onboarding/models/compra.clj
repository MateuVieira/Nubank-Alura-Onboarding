(ns nubank-alura-onboarding.models.compra
  (:require [clojure.pprint :as pp]
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

(defn calc-total
  [data]
  (reduce + data))

(defn calc-total-por-categoria
  "Esta funcao realiza a selecao dos dados de valor para o calculo do
  valor total gasto com uma determinada categoria.

  Input:
  mapa => categoria  => chave
       => compras    => mapa de compra

  Ouput:
  mapa => categoria  => chave
       => total      => int
  "
  [[categoria compras]]
  (let [valor-das-compras (map :valor compras)
        total (calc-total valor-das-compras)]
    {:categoria categoria, :total total}))

(defn print-total-por-categoria
  "Funcao dedicada a realizar o print no console dos dados de saida
  da funcao total-por-categoria.
  "
  [data]
  (pp/print-table [:categoria :total] data))

(defn total-por-categoria
  "Esta funcao realiza um calculo utilizando os dados de categoria
  de uma lista de compras, este calculo viza agrupor os dados por
  os diferentes valores e fazer a soma total por cada valor.

  Input:
  lista-de-compra  => seq de mapas de compras

  Ouput:
  Um print no console do resultado do calculo no formato de uma
  tabela.
  "
  [lista-de-compra]
  (print "\nCalculo do valor total comprado organizado por categoria:")
  (print-total-por-categoria (->>
                               lista-de-compra
                               (group-by :categoria)
                               (map calc-total-por-categoria))))

(defn convert-string-to-timestamp
  "Esta funcao converte um dado do tipo Data String para
  um valor numerico referente ao timestamp daquele dado.

  Input:
  date  =>  string  => 'yyyy-MM-dd'

  Ouput:
  timestamp => int
  "
  [date]
  (->>
    date
    (jt/local-date "yyyy-MM-dd")
    (jt/to-sql-timestamp)
    (jt/to-millis-from-epoch)))

(defn verifica-string
  "Esta funcao verifica se o dado de entrada eh do tipo string,
  se for ele converte para este dado para um tipo numerico. No
  momento so esta implementado como converter dados do tipo
  Data utilizados no mapa de compra."
  [data]
  (if (string? data)
    (convert-string-to-timestamp data)
    data))

(defn build-pred-filter
  "Esta funcao se destina a criacao de predicado para o auxilio
  da funcao filtrar-por, assim permitindo passar mais dados e
  fazer um filtragem mais complexa.

  Obs.: Ainda eh necessario se realizar uma validacao de categoria
  ja que a comparacao feita espera dado numericos e a nossa entrada
  para o caso de Data eh uma string."
  ([valor, categoria]
  (fn [compra]
    (let [valor-compra (get compra categoria)]
      (= valor valor-compra))))
  ([valor-inicial, valor-final, categoria]
   (fn [compra]
     (let [valor-compra (verifica-string (get compra categoria))
           maior-ou-igual (<= valor-inicial valor-compra)
           menor-ou-igual (>= valor-final valor-compra)]
       (and maior-ou-igual menor-ou-igual)))))

(defn eh-categoria-data?
  [categoria]
  (= categoria :data))

(defn eh-categoria-valor?
  [categoria]
  (= categoria :valor))

(defn filtrar-por
  "Esta funcao lida com o processo de filtragem de dados de uma certa categoria
  de um mapa de compra. Ela funciona com duas entradas, uma destinada a filtrar
  uma categoria por um determinado valor e a outra filtrar uma categoria por um
  range de valores.

  Input 1:
  categoria         => chaves aceitas (:data :categoria :valor :estabelecimento)
  valor             => string         (:data :categoria :estabelecimento)
                    => int            (:valor)
  lista-de-compras  => seq de mapas de compras

  Input 2:
  categoria         => chaves aceitas (:data :valor)
  valor-inicial     => string         (:data)
                    => int            (:valor)
  valor-final       => string         (:data)
                    => int            (:valor)
  lista-de-compras  => seq de mapas de compras

  Output:
  Um objeto seq com os dados filtrados segundo os parametros de entrada.
  "
  ([categoria, valor, lista-de-compras]
   (let [pred-filter (build-pred-filter valor, categoria)]
     (->>
       lista-de-compras
       (filter pred-filter))))
  ([categoria, valor-inicial, valor-final, lista-de-compras]
   (let [verificacao-data (eh-categoria-data? categoria)
         verificacao-valor (eh-categoria-valor? categoria)]
     (if (or verificacao-data verificacao-valor)
       (case categoria
         :data (do
                 (let [valor-inicial-formated  (convert-string-to-timestamp valor-inicial)
                       valor-final-formated (convert-string-to-timestamp valor-final)
                       pred-filter (build-pred-filter valor-inicial-formated, valor-final-formated, categoria)]
                    (filter pred-filter lista-de-compras)))
         :valor (do
                 (let [pred-filter (build-pred-filter valor-inicial, valor-final, categoria)]
                   (filter pred-filter lista-de-compras))))
       (println "Só é possível realizar essa filtragem com os campos Data e Valor")))))

(defn pega-mes?
  "Esta funcao eh utilizada como predicado para a realizacao do filtro
  de data de uma lista de compras. Nela será selecionado o campo Data
  da lista e com esses dado a conversão para o tipo java.time.LocalDate
  para assim fazer um processo de formatacao para retirar o valor do
  campo mes para a realizacao da verificao.

  Input:
  compra => mapa do tipo compra
  mes    => string
  "
  [compra mes]
  (let [data (get compra :data)]
    (->>
      data
      (jt/local-date "yyyy-MM-dd")
      (jt/format "MM")
      (= mes))))

(defn filtra-por-mes
  "Funcao auxiliar para a realização do filtro de calculo de fatura.
  Nesta funcao estamos recebendo o dado de mes e criando um funcao
  lambda para servir como predicado para o filtro."
  [mes]
  (fn [lista-de-compras] (pega-mes? lista-de-compras mes)))

(defn calculo-de-fatura-do-mes
  "Esta funcao realiza um filtro pelo dados da lista de compras utilizando a categoria (Chave) data
  para realizar um filtro por um determinado mes, com estes dados eh feito um calculo para saber o
  valor gasto naquele mes.

  Input:
  lista-de-compras  => seq
  mes               => string

  Output:
  Print com uma mensagem e o valor gasto."
  [lista-de-compras mes]
  (let [filter-by-mes (filter (filtra-por-mes mes) lista-de-compras)
        valor-das-compras (map :valor filter-by-mes)
        total (calc-total valor-das-compras )]
    (println "\nValor total da Fatura do mês" mes "\n->" total)))


(let [lista-de-compras (cria-mock-lista-de-compras 10)]
  (print-lista-de-compras lista-de-compras)
  ;(println "Teste novo filtro:" (filtrar-por :data, "2021-07-01", "2021-07-30", lista-de-compras))
  (print "\nTeste novo Filtro para valores:")
  (print-lista-de-compras (filtrar-por :valor, 100, 300, lista-de-compras))
  (print "\nTeste novo Filtro para datas:")
  (print-lista-de-compras (filtrar-por :data, "2021-07-01", "2021-07-30", lista-de-compras)))

(println "\n\n\n------------- END TEST ------------------\n\n")
