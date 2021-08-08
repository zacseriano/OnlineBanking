# Online Banking API

![GitHub language count](https://img.shields.io/github/languages/count/zacseriano/OnlineBanking) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zacseriano/OnlineBanking) ![GitHub repo size](https://img.shields.io/github/repo-size/zacseriano/OnlineBanking) ![GitHub last commit](https://img.shields.io/github/last-commit/zacseriano/OnlineBanking)

## Sobre a API

Uma API para operações em online banking, feita com Java, Spring Boot e Spring Framework.

## Recursos

A API tem os seguintes HTTP endpoints:

* Cadastrar Usuário: `POST/users`
* Login: `POST/auth`
* Criar Conta: `POST/account`
* Consultar Saldo: `POST/account/balance`
* Executar Transferência: `PUT/account/transfer`

### Detalhes

Para detalhes dos recursos, testes, retornos, explicações, enumerações dentre outros favor visitar a documentação presente na API do Swagger que foi configurada neste projeto:

* Swagger (API de documentação): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Tecnologias usadas

O projeto foi desenvolvido utilizando:

* **Java 11**
* **Spring Boot**
* **Maven**
* **PostgreSQL 10**
* **Swagger 2**
* **JPA**
* **Spring Security**
* **JWT**
* **JUnit 4**

### Compilação e Pacote

Crie um repositório local ou baixe o conteúdo do código, ícone presente na aba superior esquerda de nome Code e cor verde, extraia o arquivo onlinebanking.zip para a Workspace da sua IDE preferida, neste projeto foi utilizado o Eclipse. No Eclipse, siga os passos: `File` > `Import` > `Existing Maven Projects` (selecione o caminho da pasta que contém o conteúdo do arquivo extraído de onlinebanking.zip)

### Execução

Você precisa ter **PostgreSQL 10 ou versão superior** instalada na sua máquina para rodar a API. Ao instalar informe a senha desejada para o usuario `postgres` padrão. Depois de instalada, no `pgAdmin` crie um banco de dados `lanchonete`. Se você não tem `pgAdmin` instalado você pode rodar o `SQLShell`, logar no usuário padrão com a senha que você definiu e digitar os seguintes códigos

```
CREATE DATABASE banking;
```

Depois de criar o banco de dados da API, você precisa colocar no **Postgres** os dados do `username` e do `password` padrões no arquivo `application.properties` presente no `src/main/resource`. As modificações devem ser as seguintes:

```properties
spring.datasource.username=
spring.datasource.password=
```
### Rodando a API

Crie um repositório Git no seu PC, após o download, no Eclipse carregue o diretório pelos comandos de `File` > `Import` > `Existing Maven Projects`. Após o download das dependências utilize o comando `Run As Java Application` na classe `OnlinebankingApllication`.

Por padrão, a API estará disponível em [http://localhost:8080/](http://localhost:8080/)

### Documentação

* Swagger (API de documentação): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
