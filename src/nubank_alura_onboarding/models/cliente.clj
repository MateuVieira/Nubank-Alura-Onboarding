(ns nubank-alura-onboarding.models.cliente)

(defn cria-novo-cliente
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
  [nome, cpf, email]
  {:nome nome
   :cpf cpf
   :email email})

;(println "Teste cria cliente:")
;(map println (cria-novo-cliente "Mateus" "0000.0000.0000-00" "teste@nubank.com.br"))