# Car Sharing Service 🚗

![Car Sharing Illustration](images/wypozyczalnia-samochodow-osobowych-i-dostawczych.jpg))

## Project Description
Car Sharing Service is a web application that allows users to rent cars online. The project was created as part of the **Java Developer** course at **Mate Academy** and demonstrates practical usage of Spring Boot, JPA, Spring Security, JWT, and design patterns.

The application supports:
- car management (CRUD operations for MANAGER role),
- user registration and profile management,
- creating and managing rentals,
- payment system integration (Stripe simulation),
- filtering and searching data using JPA specifications.

---

## Features
- **Cars**
    - Browse available cars
    - Create, update, and delete cars (MANAGER only)
    - Filter by brand, type, availability, and daily price

- **Users**
    - Registration and profile update
    - Retrieve user information
    - Roles: `MANAGER` and `CUSTOMER`

- **Rentals**
    - Create and manage rentals
    - Calculate total price based on rental days
    - Track active rental status

- **Payments**
    - View payments
    - Payment session simulation
    - Filter payments by user

- **Security**
    - JWT-based authorization
    - Password hashing with BCrypt
    - Role-based access restrictions

---

## Technologies
- Java 17
- Spring Boot 3.3
- Spring Data JPA
- Spring Security + JWT
- MapStruct
- MySQL / H2
- Maven
- Liquibase
- Swagger/OpenAPI

---

## Installation and Running

1. **Clone the repository**
   ```bash
   git clone <REPOSITORY_URL>
   cd jv-car-sharing-service/car-sharing