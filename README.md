# UserHub Service

A RESTful API service for user management built with Spring Boot. This service provides endpoints for creating, reading, updating, and deleting user records.

## Table of Contents

- [Technologies](#technologies)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Getting Started](#getting-started)
- [Configuration](#configuration)

## Technologies

| Technology | Version |
|------------|---------|
| Java | 25 |
| Spring Boot | 4.0.2 |
| H2 Database | 2.4.x |
| Hibernate | 7.2.x |
| Lombok | Latest |
| Maven | 3.x |

## Architecture

This project follows a **layered architecture** pattern, separating concerns into distinct layers:

```
┌─────────────────────────────────────────────────────────────┐
│                      Controller Layer                       │
│              (REST endpoints, request handling)             │
├─────────────────────────────────────────────────────────────┤
│                        DTO Layer                            │
│          (Data Transfer Objects for API contracts)          │
├─────────────────────────────────────────────────────────────┤
│                      Business Layer                         │
│              (Business logic, service classes)              │
├─────────────────────────────────────────────────────────────┤
│                    Infrastructure Layer                     │
│            (Entities, Repositories, DB access)              │
└─────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

#### Controller Layer
- Handles HTTP requests and responses
- Maps endpoints to service methods
- Performs request/response transformation using DTOs
- Located in `controller/` package

#### DTO Layer
- **Request DTOs**: Define the structure of incoming data from clients
- **Response DTOs**: Define the structure of outgoing data to clients
- Provides separation between API contracts and internal entities
- Located in `dto/request/` and `dto/response/` packages

#### Business Layer
- Contains business logic and rules
- Orchestrates operations between controllers and repositories
- Handles transactions and data validation
- Located in `business/` package

#### Infrastructure Layer
- **Entities**: JPA entities representing database tables
- **Repositories**: Spring Data JPA interfaces for database operations
- Located in `infrastructure/entitys/` and `infrastructure/repositories/` packages

## Project Structure

```
src/main/java/com/cruzgiovanni/userhub_service/
├── UserhubServiceApplication.java          # Application entry point
├── business/
│   └── UserService.java                    # User business logic
├── controller/
│   └── UserController.java                 # REST API endpoints
├── dto/
│   ├── request/
│   │   └── UserRequestDTO.java             # Input data structure
│   └── response/
│       └── UserResponseDTO.java            # Output data structure
└── infrastructure/
    ├── entitys/
    │   └── User.java                       # JPA entity
    └── repositories/
        └── UserRepository.java             # Data access interface

src/main/resources/
└── application.properties                   # Application configuration
```

## Dependencies

### Production Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-webmvc` | Web MVC framework for building REST APIs |
| `spring-boot-starter-data-jpa` | JPA support with Hibernate for database operations |
| `spring-boot-h2console` | H2 database web console for development |
| `h2` | In-memory database for development and testing |
| `lombok` | Reduces boilerplate code (getters, setters, constructors, builders) |

### Test Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-data-jpa-test` | Testing utilities for JPA repositories |
| `spring-boot-starter-webmvc-test` | Testing utilities for web layer |

### Lombok Annotations Used

| Annotation | Description |
|------------|-------------|
| `@Getter` / `@Setter` | Generates getter and setter methods |
| `@NoArgsConstructor` | Generates no-argument constructor |
| `@AllArgsConstructor` | Generates all-argument constructor |
| `@RequiredArgsConstructor` | Generates constructor for final fields (used for dependency injection) |
| `@Builder` | Implements the Builder pattern |

## API Endpoints

### Base URL
```
http://localhost:8080/user
```

### Endpoints

#### Create User
```http
POST /user
Content-Type: application/json

{
    "email": "user@example.com",
    "name": "John Doe"
}
```

**Response:** `200 OK`

---

#### Get User by ID
```http
GET /user/id?id={userId}
```

**Response:**
```json
{
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe"
}
```

---

#### Get User by Email
```http
GET /user/email?email={userEmail}
```

**Response:**
```json
{
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe"
}
```

---

#### Update User
```http
PUT /user?id={userId}
Content-Type: application/json

{
    "email": "newemail@example.com",
    "name": "Jane Doe"
}
```

**Response:** `200 OK`

---

#### Delete User
```http
DELETE /user?email={userEmail}
```

**Response:** `200 OK`

## Database

### H2 In-Memory Database

This project uses H2 as an in-memory database for development purposes. Data is stored in memory and will be lost when the application stops.

### User Table Schema

| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT |
| `email` | VARCHAR(255) | NOT NULL, UNIQUE |
| `name` | VARCHAR(255) | NOT NULL |

### H2 Console Access

The H2 web console is available at:
```
http://localhost:8080/h2-console
```

**Connection Details:**
- **JDBC URL:** `jdbc:h2:mem:userhubdb`
- **Username:** `sa`
- **Password:** *(empty)*

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.x

### Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd userhub-service
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### Running Tests

```bash
mvn test
```

## Configuration

### application.properties

| Property | Description | Default Value |
|----------|-------------|---------------|
| `spring.application.name` | Application name | `userhub-service` |
| `spring.h2.console.enabled` | Enable H2 web console | `true` |
| `spring.h2.console.path` | H2 console URL path | `/h2-console` |
| `spring.datasource.url` | Database connection URL | `jdbc:h2:mem:userhubdb` |
| `spring.datasource.driverClassName` | JDBC driver class | `org.h2.Driver` |
| `spring.datasource.username` | Database username | `sa` |
| `spring.datasource.password` | Database password | *(empty)* |

### Switching to a Production Database

To use a production database (e.g., PostgreSQL, MySQL), update `application.properties`:

```properties
# PostgreSQL Example
spring.datasource.url=jdbc:postgresql://localhost:5432/userhubdb
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Remember to add the corresponding database driver dependency to `pom.xml`.
