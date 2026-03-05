# Car Sharing Service 🚗

![Car Sharing Illustration](images/wypozyczalnia-samochodow-osobowych-i-dostawczych.jpg))

## Project Description
Car Sharing Service is a web application that allows users to rent cars online.  
The project was created as part of the **Java Developer course at Mate Academy** and demonstrates practical usage of **Spring Boot, Spring Data JPA, Spring Security, JWT authentication, and REST API design**.

The application allows users to browse available cars, create rentals, and manage their profile. Managers can manage the car inventory and view rental data.

The project follows a layered architecture with controllers, services, repositories, DTOs, and mappers.

---

## Features

### Cars
- Browse available cars
- Filter cars by brand, type, availability, and maximum daily price
- Create, update, and delete cars (**MANAGER only**)

### Users
- User registration
- Retrieve and update user profile
- Role-based access control (`MANAGER`, `CUSTOMER`)

### Rentals
- Create a rental for a selected car
- Track rental status (active or completed)
- Return a rented car
- Automatic inventory update when renting and returning a car
- Calculate total rental price based on rental duration

### Payments
- Retrieve payments
- View payments for a specific user
- Basic payment flow structure prepared for external payment integration

### Security
- Authentication with **Spring Security**
- **JWT-based authorization**
- Role-based endpoint protection

---

## Technologies
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- JWT
- MapStruct
- MySQL / H2
- Maven
- Swagger / OpenAPI

---

## Installation and Running

1. **Clone the repository**
   ```bash
   git clone <REPOSITORY_URL>
   cd jv-car-sharing-service/car-sharing