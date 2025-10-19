# CooperFilme Backend

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.9.1-red?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-24.0.5-blue?logo=docker)
![Swagger](https://img.shields.io/badge/Swagger-UI-yellow?logo=swagger)

Backend do projeto **CooperFilme**, desenvolvido com **Spring Boot 3.5.6** e **Java 17**.

O projeto utiliza **PostgreSQL** como banco de dados e possui scripts para criação de tabelas e inserção de dados iniciais.

---

## Tecnologias

- **Spring Boot** 3.5.6
- **Java** 17
- **PostgreSQL**
- **Spring Data JPA**
- **Spring Security**
- **Springdoc OpenAPI / Swagger**
- **Lombok**
- **JWT (Json Web Token)**

---

## Banco de Dados

O projeto utiliza PostgreSQL com as seguintes credenciais:

- **URL:** `jdbc:postgresql://db:5432/cooperfilme`
- **Username:** `cooperfilme`
- **Password:** `cooperfilme234`

### Scripts

- **Criação de tabelas:** `schema.sql`
- **Inserção de dados iniciais:** `data.sql`

> Os scripts estão na pasta `src/main/resources/` e são carregados automaticamente pelo Spring Boot durante o startup.

---

## Credenciais de teste no sistema

### Analistas
| Login | Senha |
|-------|-------|
| analista@cooperfilme.com | analista123 |

### Revisores
| Login | Senha |
|-------|-------|
| revisor@cooperfilme.com | revisor123 |

### Aprovadores
| Login | Senha |
|-------|-------|
| aprovador1@cooperfilme.com | aprovador1123 |
| aprovador2@cooperfilme.com | aprovador2123 |
| aprovador3@cooperfilme.com | aprovador3123 |

> Essas contas já estão inseridas pelo `data.sql` e podem ser usadas para testes de diferentes perfis de usuário.

---

## Configuração do Projeto

### Rodando com Docker

Para buildar e subir a aplicação junto com o banco, basta executar:

```bash
docker compose up --build
