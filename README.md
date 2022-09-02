# Projeto cadastro de Contas e Transações

Projeto para Tokyo Marine
Tecnologias utilizadas Java 8

Pode ser aceessado através de [Github Pages](https://github.com/andersonconstante/tmcontas). 

# Escopo do Projeto

Criar um projeto de cadastro de contas e Transações, utilizando a MVC (pela familiriadade) e com as seguintes funcionalidades:

- Criar uma funcionalidade de cadastrar conta com os campos nomeCliente, nomeBanco, numeroBanco, numeroAgencia, numeroConta",
- Criar uma funcionalidade de listar as contas criadas,
- Criar uma funcionalidade de transação entre duas contas, com os campos contaOrigem, contaDestino, valorTransferencia, taxa, dataGendamento, dataTransferencia, tipo e com a finalidade de agendar e aplicando taxas de acordo com o período, tipo de trançação ou valor,
- Criar uma funcionalidade de listar as transações com a o nome do cliente da contaOrigem sendo  exibido em cada transação que ele realizou,
- Criar a rota linkando uma página a outra (Adicionar Pessoa e Listar Pessoa);
- disponibilizar a API para ser consumida em Angular:
	- API de Listagem
	- API de Cadastro

## Tecnologias

- Java 8
- IDE Spring Tool Suite 4 
  Versão: 4.6.1.RELEASE
- SpringBoot
- JPA
- Banco H2 integrado no SpringBoot e em memória


## Como instalar

- Baixe ou clone este repositório usando `git clone https://github.com/andersonconstante/tmcontas.git`;
- Caso não tenha JDk, será necessário instalar do site da Oracle https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html

## Como executar

- Basta clicar no botão de executar
- Por padrão será executado em http://localhost:8080/
- Se exibir uma pagina com uma mensagem, o servidor está executando.
- Também é possivel consultar as tabelas de Contas e transações pelo console do H2 basta digitar no navegador http://localhost:8080/h2-console/login.jsp
- Basta clicar em login e será redirecionado para o console,


## Dúvidas
Caso há alguma dúvida em relação a este repositório, envie para andersonwconsul@gmail.com
