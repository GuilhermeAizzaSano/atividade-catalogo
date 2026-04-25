# Atividade Catalogo (Spring Boot)

Projeto Java + Spring Boot para gestao de catalogo (produtos, categorias e usuarios) com seguranca por perfil.

## Requisitos
- Java 17+ (testado com Java 25)
- PostgreSQL rodando localmente

## 1) Clonar o projeto
```bash
git clone https://github.com/GuilhermeAizzaSano/atividade-catalogo.git
cd atividade-catalogo
```

## 2) Criar banco no PostgreSQL
Crie o banco com o nome abaixo:
```sql
CREATE DATABASE "catalogoBD";
```

## 3) Configurar o `application.properties`
O arquivo principal fica em:
`src/main/resources/application.properties`

O projeto ja vem com fallback por variavel de ambiente:
- `DB_URL` (padrao: `jdbc:postgresql://localhost:5432/catalogoBD`)
- `DB_USER` (padrao: `postgres`)
- `DB_PASSWORD` (padrao: `123456`)

Se preferir, use o modelo:
`src/main/resources/application.properties.example`

Exemplo:
```properties
spring.application.name=catalogo

spring.datasource.url=jdbc:postgresql://localhost:5432/catalogoBD
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 4) Rodar a aplicacao
No Windows:
```bash
mvnw.cmd spring-boot:run
```

No Linux/Mac:
```bash
./mvnw spring-boot:run
```

Acesse:
- http://localhost:8080

## Login inicial
O sistema cria automaticamente um usuario admin no primeiro start:
- Usuario: `admin`
- Senha: `12345`

## Observacao
Se a porta 8080 estiver ocupada, finalize o processo da porta ou altere a porta no `application.properties`.
