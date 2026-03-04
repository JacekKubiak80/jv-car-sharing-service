# Car Sharing Application

Simple car rental backend application built with Spring Boot. Educational project for learning REST API and Spring Security.

## Features

- user registration and profile update
- roles: MANAGER / CUSTOMER
- car management (CRUD for MANAGER)
- car filtering (brand, type, inventory, daily fee)
- rental overview
- payment overview
- JWT authentication
- role-based authorization with Spring Security
- request validation

## Technologies

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MapStruct
- Lombok
- Swagger / OpenAPI
- Database (e.g. MySQL or H2)

## Example Endpoints

- `POST /users/register` – register new user
- `GET /users/me` – get current user profile
- `GET /cars` – list cars (with optional filters)
- `POST /cars` – create car (MANAGER only)
- `GET /rentals` – list all rentals (MANAGER only)
- `GET /payments/all` – list all payments (MANAGER only)

## Security

- JWT (Bearer token) authentication
- `@PreAuthorize` for role-based access
- passwords encrypted with BCrypt

In `application.properties`:
jwt.secret=your_secret_key
jwt.expiration=3600000


## How to Run

1. Configure database in `application.properties`
2. Run: `mvn clean install`
3. Run: `mvn spring-boot:run`

Application runs by default on `localhost:8080`.
