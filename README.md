# 🛒 E-Commerce API

API completa para sistema de E-Commerce desenvolvida em Java com Spring Boot.

## 🚀 Funcionalidades

- ✅ CRUD completo de **Produtos**
- ✅ CRUD completo de **Categorias**  
- ✅ CRUD completo de **Clientes**
- ✅ CRUD completo de **Endereços**
- ✅ Consultar produtos por categoria
- ✅ Relacionamento Cliente → Endereços (1:N)
- ✅ Validações de entrada
- ✅ Tratamento de exceções
- ✅ Documentação Swagger
- ✅ Soft Delete

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Swagger/OpenAPI**
- **ModelMapper**
- **Bean Validation**

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

## 🏗️ Instalação e Execução

### 1. Clonar o repositório
```bash
git clone <url-do-repositorio>
cd ecommerce-api
2. Configurar PostgreSQL
CREATE DATABASE postgres;
CREATE USER chan WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE ecommerce_db TO ecommerce;
3. Executar a aplicação
bashmvn clean install
mvn spring-boot:run