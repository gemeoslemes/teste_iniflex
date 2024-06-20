# Projeto Iniflex  🚀
## Teste de Programação 🖥️

Este é um projeto de exemplo utilizando Java, Spring Boot e MySQL. O projeto utiliza diversas tecnologias para desenvolvimento web e persistência de dados.

## Visão Geral 👁️‍🗨️

Este projeto foi desenvolvido para demonstrar práticas comuns de desenvolvimento de aplicações web utilizando Java com Spring Boot e integração com um banco de dados MySQL.

## Pré-requisitos 🛠️

Antes de começar, certifique-se de ter instalado em sua máquina:

- **Java Development Kit (JDK)** - Versão 17 ou superior. [Download JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Maven** - Gerenciador de dependências para compilação do projeto. [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL Server** - Para armazenamento de dados. [Download MySQL](https://dev.mysql.com/downloads/)
  - **Ou utilize uma imagem Docker do MySQL:**
    
    ```bash
    docker run --name mysql-server -p 3306:3306 -e MYSQL_ROOT_PASSWORD=sua_senha -d mysql:latest
    ```

## Instalação e Configuração ⚙️

1. **Clonar o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/iniflex.git
   cd iniflex

2. **Configurar Banco de Dados**
    - Certifique-se de ter um servidor MySQL em execução.
    - Configure as credenciais do banco de dados no arquivo:
    - `src/main/resources/application.yml.`
      
    - Exemplo de configuração(`application.yml`):
      
    ```bash
      security:
        jwt:
          token:
            secret-key: sua_chave_secreta
            expire-length: 3600000
      spring:
        datasource:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/nome_database?useTimezone=true&serverTimezone=UTC
          username: seu_root
          password: sua_password
      jpa:
        open-in-view: false
        hibernate:
          ddl-auto: none
        show-sql: false
      springdoc:
        pathsToMatch:
          - /auth/**
          - /api/**/v1/**
      swagger-ui:
        use-root-path: true

3. **Executando o Projeto**
   Para executar o projeto:
   - 1 Entre no diretório:
     
     ```bash
     cd iniflix
     ```
   - 2 Use o seguinte comando Maven:
     
     ```bash
       mvn spring-boot:run
     ```
    Isso iniciará a aplicação Spring Boot. Acesse `http://localhost:8080` para visualizar a aplicação em execução.
   
### **Documentação da API** 📚
  A documentação da API pode ser acessada através do Swagger UI. Após iniciar o projeto, abra o seguinte URL em seu navegador:
  
  ```bash
    http://localhost:8080/swagger-ui/index.html
  ```
### **Postman** 📫
  1. **Certifique-se de ter instalado o Postman na sua máquina ou acesse via web:**
     - Faça o [download e instalação do Postman](https://www.postman.com/downloads/) no seu computador.
     - Acesse o [Postman diretamente na web](https://www.postman.com/).
    
  2. **Importe a coleção da API no Postman:**
     - Abra o Postman.
     - Clique em `Import` (Importar) na barra de navegação.
     - Selecione a opção `Link`.
     - Insira a URL da documentação OpenAPI/Swagger da sua API para importação.
       
       ```bash
       http://localhost:8080/v3/api-docs
       ```

# Documentação da API RESTFull Java 17 and Spring Boot 3.3.0 📝

## Introdução 🎯

Esta é a documentação da API RESTFull desenvolvida com Java 17 e Spring Boot 3.3.0. A API oferece endpoints para gerenciamento de funcionários.

### Informações Gerais ℹ️

- **Documentação da API**: [http://iniflex.backend.com](http://iniflex.backend.com)
- **Versão da API**: v1
- **Licença**: [Apache 2.0](http://www.apache.org/licenses/)

## Endpoints 🚪

Aqui estão os principais endpoints disponíveis na API:

## Endpoint de Autenticação 🔑
```json
{
  "method": "POST",
  "url": "http://localhost:8080/auth/signin",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "username": "seu_usuario",
    "password": "sua_senha"
  }
}
```

## Atualizar Token (Refresh Token) 🔄
  ```json
  {
    "method": "PUT",
    "url": "http://localhost:8080/auth/refresh/{username}",
    "headers": {
      "Authorization": "Bearer seu_token_aqui"
    }
  }
```

Atualiza o token para o usuário autenticado e retorna um novo token.

**Explicação:**
- **URL**: `/auth/refresh/{username}`
- **Método**: PUT
- **Parâmetros Path**:
  - `username` (obrigatório, tipo: string)
- **Headers**:
  - `Authorization` (obrigatório, tipo: string)
- **Respostas**:
  - `200 OK`: Token atualizado com sucesso
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

# Funcionários 👨‍💼

## Buscar Todos os Funcionários 🔍
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1?page=0&size=10&direction=asc"
  }
```

Busca todos os funcionários com suporte a paginação.

**Explicação:**
- **URL**: `/api/funcionarios/v1`
- **Método**: GET
- **Parâmetros Query**:
  - `page` (opcional, tipo: integer, padrão: 0)
  - `size` (opcional, tipo: integer, padrão: 10)
  - `direction` (opcional, tipo: string, padrão: "asc")
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Atualizar Salário de Todos os Funcionários 💰
```json
{
  "method": "PUT",
  "url": "http://localhost:8080/api/funcionarios/v1",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "increase": 10.0
  }
}
```

Atualiza o salário de todos os funcionários.

**Explicação:**
- **URL**: `/api/funcionarios/v1`
- **Método**: PUT
- **Corpo da Requisição**:
  - Tipo de Conteúdo: application/json ou application/xml
  - Esquema: IncreaseRequestDTO
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Criar Funcionário ✍️
```json
{
  "method": "POST",
  "url": "http://localhost:8080/api/funcionarios/v1",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "nome": "Nome do Funcionário",
    "dataNascimento": "1990-01-01",
    "salario": 3000.0,
    "funcao": "Desenvolvedor"
  }
}
```
Cria um novo funcionário.

**Explicação:**
- **URL**: `/api/funcionarios/v1`
- **Método**: POST
- **Corpo da Requisição**:
  - Tipo de Conteúdo: application/json ou application/xml
  - Esquema: FuncionarioDTO
- **Respostas**:
  - `201 Created`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Buscar Funcionário por ID 🆔
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/{id}"
  }
```
Busca um funcionário pelo ID.

**Explicação:**
- **URL**: `/api/funcionarios/v1/{id}`
- **Método**: GET
- **Parâmetros Path**:
  - `id` (obrigatório, tipo: integer)
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Deletar Funcionário  🗑️
```json
  {
    "method": "DELETE",
    "url": "http://localhost:8080/api/funcionarios/v1/{id}"
  }
```
Deleta um funcionário pelo ID.

**Explicação:**
- **URL**: `/api/funcionarios/v1/{id}`
- **Método**: DELETE
- **Parâmetros Path**:
  - `id` (obrigatório, tipo: integer)
- **Respostas**:
  - `204 No Content`: Sucesso
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Calcular Salário Global dos Funcionários 💼
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/salario-total"
  }
```
Calcula a soma total dos salários de todos os funcionários. 

**Explicação:**
- **URL**: `/api/funcionarios/v1/salario-total`
- **Método**: GET
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Calcular Salários Mínimos Individuais dos Funcionários ⏬
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/salario-minimo?page=0&size=10&direction=asc"
  }
```
Calcula quantos salários mínimos cada funcionário possui.

**Explicação:**
- **URL**: `/api/funcionarios/v1/salario-minimo`
- **Método**: GET
- **Parâmetros Query**:
  - `page` (opcional, tipo: integer, padrão: 0)
  - `size` (opcional, tipo: integer, padrão: 10)
  - `direction` (opcional, tipo: string, padrão: "asc")
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Listar Funcionários em Ordem Alfabética 📃
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/ordem-alfabetica?page=0&size=10&direction=asc"
  }
```
Lista todos os funcionários em ordem alfabética.

**Explicação:**
- **URL**: `/api/funcionarios/v1/ordem-alfabetica`
- **Método**: GET
- **Parâmetros Query**:
  - `page` (opcional, tipo: integer, padrão: 0)
  - `size` (opcional, tipo: integer, padrão: 10)
  - `direction` (opcional, tipo: string, padrão: "asc")
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Encontrar Funcionário com Maior Idade 👴
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/maior-idade"
  }
```
Busca o funcionário mais velho.

**Explicação:**
- **URL**: `/api/funcionarios/v1/maior-idade`
- **Método**: GET
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Agrupar Funcionários por Função 🧑‍💻
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/funcao"
  }
```
Agrupa funcionários por função.

**Explicação:**
- **URL**: `/api/funcionarios/v1/funcao`
- **Método**: GET
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

## Encontrar Funcionários com Aniversários em Outubro e Dezembro 🎂
```json
  {
    "method": "GET",
    "url": "http://localhost:8080/api/funcionarios/v1/aniversarios?page=0&size=10&direction=asc"
}
```
Busca funcionários cujos aniversários são em outubro e dezembro.

**Explicação:**
- **URL**: `/api/funcionarios/v1/aniversarios`
- **Método**: GET
- **Parâmetros Query**:
  - `page` (opcional, tipo: integer, padrão: 0)
  - `size` (opcional, tipo: integer, padrão: 10)
  - `direction` (opcional, tipo: string, padrão: "asc")
- **Respostas**:
  - `200 OK`: Sucesso
    - Tipo de Conteúdo: application/json
    - Esquema: FuncionarioVO
  - `400 Bad Request`: Requisição inválida
  - `401 Unauthorized`: Não autorizado
  - `404 Not Found`: Recurso não encontrado
  - `500 Internal Error`: Erro interno do servidor

# Conclusão 
Utilizando o exemplo acima, você pode facilmente autenticar um usuário na sua API RESTful utilizando o Postman. O token JWT retornado pode ser utilizado para realizar operações subsequentes que requerem autenticação, garantindo segurança e controle de acesso aos recursos da aplicação.

Ao testar suas APIs com Postman, certifique-se de configurar corretamente os endpoints e os dados de autenticação para obter tokens válidos e completar suas interações com a API de forma eficaz.

Essa abordagem não só ajuda no desenvolvimento e teste de APIs, mas também proporciona uma maneira simples e direta de entender como as requisições HTTP funcionam dentro do contexto de uma aplicação RESTful.

