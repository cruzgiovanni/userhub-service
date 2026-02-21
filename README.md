# UserHub Service

A RESTful API service for user management built with Spring Boot. This service provides endpoints for user authentication (register/login with JWT) and user/post management.

## Table of Contents

- [Technologies](#technologies)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Security](#security)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Getting Started](#getting-started)
- [Configuration](#configuration)

## Technologies

| Technology | Version |
|------------|---------|
| Java | 25 |
| Spring Boot | 4.0.2 |
| Spring Security | (managed by Spring Boot) |
| H2 Database | 2.4.x |
| Hibernate | 7.2.x |
| Lombok | Latest |
| Maven | 3.x |
| java-jwt (Auth0) | 4.5.1 |

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
│       (Entities, Repositories, Security, DB access)         │
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
- Located in `services/` package

#### Infrastructure Layer
- **Entities**: JPA entities representing database tables
- **Repositories**: Spring Data JPA interfaces for database operations
- **Security**: JWT filter, token service, and security configuration
- Located in `infrastructure/` package

## Project Structure

```
src/main/java/com/cruzgiovanni/userhub_service/
├── UserhubServiceApplication.java
├── controller/
│   ├── AuthController.java          # Register and login endpoints
│   ├── UserController.java          # User management endpoints
│   └── PostController.java          # Post management endpoints
├── dto/
│   ├── request/
│   │   ├── AuthDTO.java             # Login request (login + password)
│   │   ├── RegisterDTO.java         # Register request (login, email, name, password, role)
│   │   ├── UserRequestDTO.java      # User update request
│   │   └── PostRequestDTO.java      # Post request
│   └── response/
│       ├── LoginResponseDTO.java    # JWT token response
│       ├── UserResponseDTO.java     # User response
│       └── PostResponseDTO.java     # Post response
├── services/
│   ├── AuthService.java             # UserDetailsService implementation
│   ├── UserService.java             # User business logic
│   └── PostService.java             # Post business logic
└── infrastructure/
    ├── entities/
    │   ├── User/
    │   │   ├── User.java            # User JPA entity
    │   │   └── UserRole.java        # ADMIN / USER enum
    │   └── Post.java                # Post JPA entity
    ├── repositories/
    │   ├── UserRepository.java
    │   └── PostRepository.java
    └── security/
        ├── SecurityConfiguration.java  # Filter chain and authorization rules
        ├── SecurityFilter.java         # JWT token validation filter
        └── TokenService.java           # JWT generation and validation

src/main/resources/
└── application.properties
```

## Dependencies

### Production Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-webmvc` | Web MVC framework for building REST APIs |
| `spring-boot-starter-data-jpa` | JPA support with Hibernate for database operations |
| `spring-boot-starter-security` | Authentication and authorization |
| `com.auth0:java-jwt` | JWT token generation and validation |
| `spring-boot-h2console` | H2 database web console for development |
| `h2` | In-memory database for development and testing |
| `lombok` | Reduces boilerplate code (getters, setters, constructors, builders) |

### Test Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-data-jpa-test` | Testing utilities for JPA repositories |
| `spring-boot-starter-webmvc-test` | Testing utilities for web layer |
| `spring-boot-starter-security-test` | Testing utilities for Spring Security |

### Lombok Annotations Used

| Annotation | Description |
|------------|-------------|
| `@Getter` / `@Setter` | Generates getter and setter methods |
| `@NoArgsConstructor` | Generates no-argument constructor |
| `@AllArgsConstructor` | Generates all-argument constructor |
| `@RequiredArgsConstructor` | Generates constructor for final fields (used for dependency injection) |
| `@Builder` | Implements the Builder pattern |

## Security

Authentication is handled via **JWT (JSON Web Token)**. The flow is:

1. User registers via `POST /auth/register`
2. User logs in via `POST /auth/login` and receives a JWT token
3. All protected endpoints require the token in the `Authorization` header:

```
Authorization: Bearer <token>
```

### Roles

| Role | Description |
|------|-------------|
| `USER` | Standard user with access to posts and profile |
| `ADMIN` | Administrator role |

### Endpoint Authorization

| Endpoint | Access |
|----------|--------|
| `POST /auth/login` | Public |
| `POST /auth/register` | Public |
| `POST /post` | USER |
| `GET /post/**` | USER |
| `DELETE /post` | USER |
| `PUT /user/**` | USER |
| `DELETE /user` | USER |
| All others | Authenticated |

## API Endpoints

### Auth

#### Register
```http
POST /auth/register
Content-Type: application/json

{
    "login": "cruzgiovanni",
    "email": "cruz@example.com",
    "name": "Cruz Giovanni",
    "password": "senha123",
    "role": "USER"
}
```

**Response:** `200 OK`

---

#### Login
```http
POST /auth/login
Content-Type: application/json

{
    "login": "cruzgiovanni",
    "password": "senha123"
}
```

**Response:** `200 OK`
```json
{
    "token": "<jwt-token>"
}
```

---

### User

> All user endpoints require `Authorization: Bearer <token>`

#### Get User by ID
```http
GET /user/{id}
```

**Response:**
```json
{
    "id": 1,
    "email": "cruz@example.com",
    "name": "Cruz Giovanni"
}
```

---

#### Get User by Email
```http
GET /user/email/{email}
```

**Response:**
```json
{
    "id": 1,
    "email": "cruz@example.com",
    "name": "Cruz Giovanni"
}
```

---

#### Update User
```http
PUT /user/{id}
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
DELETE /user/{email}
```

**Response:** `200 OK`

---

## Database

### H2 In-Memory Database

This project uses H2 as an in-memory database for development purposes. Data is stored in memory and will be lost when the application stops.

### User Table Schema

| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT |
| `login` | VARCHAR(255) | NOT NULL, UNIQUE |
| `email` | VARCHAR(255) | NOT NULL |
| `name` | VARCHAR(255) | NOT NULL |
| `password` | VARCHAR(255) | NOT NULL (BCrypt hashed) |
| `role` | VARCHAR(50) | NOT NULL (`ADMIN` or `USER`) |

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
| `spring.datasource.password` | Database password | *(configured in properties)* |
| `api.security.token.secret` | Secret key for JWT signing | *(configured in properties)* |

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
