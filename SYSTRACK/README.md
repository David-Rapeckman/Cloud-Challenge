# 🚀 SysTrack

**SysTrack** é uma aplicação desenvolvida com **Java (Spring Boot)** para gerenciar pátios de veículos e suas respectivas motocicletas, oferecendo filtros personalizados, paginação e ordenação de dados para facilitar a administração de frota.

## 📌 Índice

- [🧾 Sobre o Projeto](#-sobre-o-projeto)
- [⚙️ Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [🧪 Como Executar](#-como-executar)
- [📌 Endpoints da API](#-endpoints-da-api)
- [✅ Funcionalidades](#-funcionalidades)
- [🗃️ Modelo de Dados](#-modelo-de-dados)
- [👨‍💻 Nossa equipe](#-nossa-equipe)

---

## 🧾 Sobre o Projeto

O objetivo do **SysTrack** é fornecer uma API RESTful robusta para cadastro, listagem e filtragem de **pátios** e **motocicletas**, com validações e regras de negócio bem definidas. A aplicação é organizada seguindo boas práticas do Spring Boot, com uso de Specifications para filtros dinâmicos, DTOs para abstração de dados, e integração com o Swagger.

---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database (banco em memória)
- Lombok
- Jakarta Validation
- Swagger/OpenAPI
- Maven

---

## 🧪 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Passos

```bash
# Clone o repositório
git clone https://github.com/guurangel/SysTrack.git

# Acesse a pasta do projeto
cd SysTrack

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

A API estará disponível em:  
📍 `http://localhost:8080`

Acesse o Swagger para testar os endpoints:  
📘 `http://localhost:8080/swagger-ui.html`

---

## 📌 Endpoints da API

### 🏍️ Motorcycle

- `GET /motorcycles` — Lista todas as motos (com filtros e paginação)
- `POST /motorcycles` — Cadastra uma nova moto
- `GET /motorcycles/{id}` — Busca por ID
- `PUT /motorcycles/{id}` — Atualiza dados
- `DELETE /motorcycles/{id}` — Remove uma moto

**Filtros disponíveis (como parâmetros da URL):**

- `plate` — placa (parcial ou completa)
- `brand` — marca
- `model` — modelo
- `status` — status da moto (`Funcional` ou `Manutenção`)
- `modelYear` — ano exato
- `modelYearStart` e `modelYearEnd` — intervalo de ano
- `kmMin` e `kmMax` — intervalo de quilometragem
- `yardId` — filtrar por pátio associado

---

### 🏗️ Yard

- `GET /yards` — Lista pátios (com filtros e paginação)
- `POST /yards` — Cadastra um novo pátio
- `GET /yards/{id}` — Detalha pátio
- `PUT /yards/{id}` — Atualiza pátio
- `DELETE /yards/{id}` — Remove pátio

**Filtros disponíveis:**

- Capacidade total máxima (`maxCapacity`)

---

## ✅ Funcionalidades

- 🔎 Filtros dinâmicos com `JpaSpecificationExecutor`
- 🧱 Organização em camadas (controller, service, repository)
- 📖 Validações detalhadas com mensagens amigáveis
- 📊 Documentação interativa via Swagger
- 📦 Paginação e ordenação nos endpoints

---

## 🗃️ Modelo de Dados

### Motorcycle

```java
id: Long
plate: String
brand: String
model: String
model_year: Integer
status: Funcional | Manutenção
km: Double
yard: Yard
```

### Yard

```java
id: Long
name: String
adress: String
maxCapacity: Integer
```

---

## 👨‍💻 Nossa equipe

**Gustavo Rangel**  
💼 Estudante de Análise e Desenvolvimento de Sistemas na FIAP  
🔗 [linkedin.com/in/gustavoorangel](https://www.linkedin.com/in/gustavoorangel)

**David Rapeckman**  
💼 Estudante de Análise e Desenvolvimento de Sistemas na FIAP  
🔗 [linkedin.com/in/davidrapeckman](https://www.linkedin.com/in/davidrapeckman)

**Luis Felippe Morais**  
💼 Estudante de Análise e Desenvolvimento de Sistemas na FIAP  
🔗 [linkedin.com/in/luis-felippe-morais-das-neves-16219b2b9](https://www.linkedin.com/in/luis-felippe-morais-das-neves-16219b2b9)
