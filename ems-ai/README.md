# EMS-AI: Employee Management System

A robust, enterprise-grade **Employee Management System** backend built with **Spring Boot 3.2.5**. This application manages Organizations, Roles, and Users with secure database persistence, validation, and Spring Security integration.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.16-336791)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Security](#security)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

---

## Features

✅ **Organization Management**
- Create organizations
- Retrieve all organizations
- Fetch specific organization by ID
- Cascade delete (removes all associated roles and users)

✅ **Role Management**
- Create roles with descriptions
- Assign roles to specific organizations
- Prevent cross-organization role assignment

✅ **User Management**
- Create users with validation (name, email, password)
- Assign roles to users
- Password hashing with BCrypt
- Retrieve users grouped by organization
- Email uniqueness per organization
- Active/Inactive user status

✅ **Security**
- Password hashing with Spring Security
- Input validation using Jakarta Validators
- Data isolation between organizations
- Foreign key constraints in database

✅ **Database**
- PostgreSQL integration
- Flyway database migrations (version-controlled schema)
- Stored procedures for complex operations
- Automatic schema validation on startup

✅ **Development Experience**
- Lombok for cleaner code (eliminates boilerplate getters/setters)
- Strong typing with UUIDs
- Comprehensive error messages
- RESTful API design

---

## Tech Stack

### Backend Framework
- **Spring Boot** 3.2.5 - Web application framework
- **Spring Web** - REST API support
- **Spring Security** - Authentication & password encoding
- **Spring Data JPA** - ORM and database access

### Database
- **PostgreSQL** 15.16 - Relational database
- **Flyway** 9.22.3 - Database version control & migrations

### ORM & Persistence
- **Hibernate** 6.4.4 - JPA implementation
- **Jakarta JPA** - Java persistence standard

### Validation & Utility
- **Jakarta Validation** - Input validation
- **Lombok** - Code generation (getters, setters, constructors)

### Build & Deployment
- **Maven** 3.x - Build automation
- **Java** 17 - Programming language

---

## Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────────────┐
│           REST API (Controllers)                │
│  OrganizationController    UserController      │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│        Business Logic (Services)                │
│ OrganizationService        UserService          │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│       Data Access (Repositories)                │
│ OrganizationRepository                          │
│ UserRepository                                  │
│ RoleRepository                                  │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│    Entity Models (JPA/Hibernate)                │
│ Organization    Role    User                    │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│     PostgreSQL Database                         │
└─────────────────────────────────────────────────┘
```

### Benefits of This Architecture

- **Separation of Concerns**: Each layer has a single responsibility
- **Testability**: Easy to unit test with mocked dependencies
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features without breaking existing code
- **Reusability**: Services and repositories can be reused across controllers

---

## Prerequisites

Before setting up the project, ensure you have:

### Required Software
- **Java 17 or higher**
  ```bash
  java -version
  ```
  Should output: `openjdk 17.0.x` or similar

- **Maven 3.6.0 or higher**
  ```bash
  mvn -version
  ```

- **PostgreSQL 12 or higher**
  ```bash
  psql --version
  ```

### Database Setup
1. PostgreSQL server running on `localhost:5433`
2. Create a database named `ems_ai`
   ```sql
   CREATE DATABASE ems_ai;
   ```
3. Ensure you have a PostgreSQL user with credentials:
   - Username: `postgres`
   - Password: `Demo@123`
   - (These are configured in `application.properties`)

---

## Installation & Setup

### 1. Clone the Repository

```bash
cd "C:\Priyanshu Sharma\Project"
# Or your project directory
```

### 2. Navigate to Project Directory

```bash
cd ems-ai
```

### 3. Verify Maven Installation

```bash
mvn --version
```

### 4. Download Dependencies

Maven will automatically download all dependencies on first build. The `pom.xml` file contains:
- Spring Boot Starters (Web, Data JPA, Security, Validation)
- PostgreSQL Driver
- Flyway for migrations
- Lombok for code generation
- Testing libraries

---

## Running the Application

### Option 1: Using Maven (Recommended)

```bash
# Clean build and run
mvn clean spring-boot:run
```

The application will:
1. Download all dependencies
2. Compile the source code
3. Run database migrations via Flyway
4. Start the embedded Tomcat server
5. Listen on `http://localhost:8081`

### Option 2: Build JAR and Run

```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/ems-ai-0.0.1-SNAPSHOT.jar
```

### Verify Application is Running

```bash
# Should return a list of organizations (empty if no data)
curl http://localhost:8081/api/organizations/getAllOrganizations
```

Expected output:
```json
[]
```

---

## API Documentation

### Base URL
```
http://localhost:8081
```

### 1. Organization Endpoints

#### Create Organization
```http
POST /api/organizations/createOrganization
Content-Type: application/json

{
  "name": "Tech Innovations Inc"
}
```

**Response:** 
```json
"550e8400-e29b-41d4-a716-446655440000"
```

**Status Codes:**
- `200 OK` - Organization created successfully
- `400 BAD REQUEST` - Invalid request (missing name)
- `500 INTERNAL SERVER ERROR` - Database error

---

#### Get All Organizations

```http
GET /api/organizations/getAllOrganizations
```

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Tech Innovations Inc"
  },
  {
    "id": "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
    "name": "Digital Solutions Ltd"
  }
]
```

---

#### Get Organization by ID

```http
GET /api/organizations/getOrganizationById/{id}
```

**Example:**
```http
GET /api/organizations/getOrganizationById/550e8400-e29b-41d4-a716-446655440000
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Tech Innovations Inc"
}
```

---

### 2. User Endpoints

#### Create User

```http
POST /organizations/{orgId}/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@techinnov.com",
  "password": "SecurePassword@123",
  "roleId": "role-uuid-here"
}
```

**Path Variables:**
- `orgId` - UUID of the organization

**Request Body Validation:**
- `name` - Required, non-empty string
- `email` - Required, valid email format
- `password` - Required, non-empty string
- `roleId` - Required, UUID of existing role in the organization

**Response:**
```json
"user-uuid-here"
```

**Validations Performed:**
1. Organization exists
2. Role exists
3. Role belongs to the specified organization
4. Email is unique within the organization
5. Password is hashed with BCrypt before storage

**Status Codes:**
- `200 OK` - User created successfully
- `400 BAD REQUEST` - Invalid input data
- `500 INTERNAL SERVER ERROR` - Organization/Role not found or database error

---

#### Get Users by Organization

```http
GET /organizations/{orgId}/users
```

**Example:**
```http
GET /organizations/550e8400-e29b-41d4-a716-446655440000/users
```

**Response:**
```json
[
  {
    "id": "user-uuid-1",
    "name": "John Doe",
    "email": "john.doe@techinnov.com",
    "isActive": true,
    "roleName": "Manager"
  },
  {
    "id": "user-uuid-2",
    "name": "Jane Smith",
    "email": "jane.smith@techinnov.com",
    "isActive": true,
    "roleName": "Developer"
  }
]
```

**Note:** Password field is **never** returned in the response for security reasons.

---

### 3. Role Management (Database/Stored Procedures)

Roles are created using PostgreSQL stored procedures. Connect to the database and execute:

```sql
-- Create a role
SELECT ems.sp_create_role('org-id-here', 'Manager');

-- Or directly insert
INSERT INTO ems.roles(org_id, name, description)
VALUES ('org-id-here', 'Manager', 'Managerial role');
```

---

## Database Schema

### Overview

```
┌─────────────────────────────────┐
│      ems.organizations          │
├─────────────────────────────────┤
│ id: UUID (Primary Key)          │
│ name: VARCHAR(255)              │
│ created_at: TIMESTAMP           │
└────────────────┬────────────────┘
                 │ (1:N) Has Many
                 │
         ┌───────┴────────┐
         │                │
    ┌────▼─────────────┐  │
    │  ems.roles       │  │
    ├──────────────────┤  │
    │ id: UUID (PK)    │  │
    │ org_id: UUID(FK) │  │
    │ name: VARCHAR    │  │
    │ description: VAR │  │
    │ created_at:TS    │  │
    └────┬─────────────┘  │
         │                │
    ┌────▼──────────────────────────┐
    │    ems.users                   │
    ├────────────────────────────────┤
    │ id: UUID (Primary Key)         │
    │ org_id: UUID (Foreign Key)─────┘
    │ role_id: UUID (Foreign Key)───┐
    │ name: VARCHAR(255)             │
    │ email: VARCHAR(255)            │
    │ password: VARCHAR(255)         │
    │ is_active: BOOLEAN             │
    │ created_at: TIMESTAMP          │
    ├────────────────────────────────┤
    │ UNIQUE(email, org_id)          │
    └────────────────────────────────┘
```

### Table Descriptions

#### ems.organizations
Stores company/organization information.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | UUID | PRIMARY KEY, DEFAULT gen_random_uuid() | Unique organization identifier |
| `name` | VARCHAR(255) | NOT NULL | Organization name |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation timestamp |

---

#### ems.roles
Stores roles that can be assigned to users within an organization.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | UUID | PRIMARY KEY, DEFAULT gen_random_uuid() | Unique role identifier |
| `org_id` | UUID | NOT NULL, FOREIGN KEY (or_id) → organizations(id) ON DELETE CASCADE | Organization reference |
| `name` | VARCHAR(100) | NOT NULL | Role name (e.g., Manager, Developer) |
| `description` | VARCHAR(500) | - | Role description (added in migration V5) |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation timestamp |

---

#### ems.users
Stores employee information.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | UUID | PRIMARY KEY, DEFAULT gen_random_uuid() | Unique user identifier |
| `org_id` | UUID | NOT NULL, FOREIGN KEY → organizations(id) ON DELETE CASCADE | Organization reference |
| `role_id` | UUID | NOT NULL, FOREIGN KEY → roles(id) | Role reference |
| `name` | VARCHAR(255) | NOT NULL | User's full name |
| `email` | VARCHAR(255) | NOT NULL | User's email address |
| `password` | VARCHAR(255) | NOT NULL | BCrypt hashed password |
| `is_active` | BOOLEAN | DEFAULT TRUE | Account active status |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation timestamp |
| **UNIQUE(email, org_id)** | - | Composite Index | Ensures email uniqueness per organization |

---

### Relationships

1. **Organization → Roles (1:N)**
   - One organization can have many roles
   - Deleting an organization cascades to its roles

2. **Organization → Users (1:N)**
   - One organization can have many users
   - Deleting an organization cascades to its users

3. **Role → Users (1:N)**
   - One role can be assigned to many users
   - Users must have a valid role in their organization

---

## Project Structure

```
ems-ai/
├── src/
│   ├── main/
│   │   ├── java/com/priyanshu/ems_ai/
│   │   │   ├── EmsAiApplication.java              # Main Spring Boot class
│   │   │   │
│   │   │   ├── controller/                         # REST API Controllers
│   │   │   │   ├── OrganizationController.java
│   │   │   │   └── UserController.java
│   │   │   │
│   │   │   ├── service/                            # Business Logic
│   │   │   │   ├── OrganizationService.java
│   │   │   │   └── UserService.java
│   │   │   │
│   │   │   ├── repository/                         # Data Access Layer (JPA)
│   │   │   │   ├── OrganizationRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   └── UserRespository.java            # (Duplicate filename)
│   │   │   │
│   │   │   ├── entity/                             # JPA Entity Models
│   │   │   │   ├── Organization.java
│   │   │   │   ├── User.java
│   │   │   │   └── Role.java
│   │   │   │
│   │   │   ├── dto/                                # Data Transfer Objects
│   │   │   │   ├── CreateOrganizationRequest.java
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   └── UserResponse.java
│   │   │   │
│   │   │   ├── config/                             # Spring Configuration
│   │   │   │   └── SecurityConfig.java
│   │   │   │
│   │   │   ├── annotation/                         # Custom Annotations
│   │   │   ├── constants/                          # Application Constants
│   │   │   ├── exception/                          # Exception Handling
│   │   │   ├── mapper/                             # Data Mapping (DTOs ↔ Entities)
│   │   │   ├── model/                              # Additional Models
│   │   │   ├── testutils/                          # Testing Utilities
│   │   │   ├── util/                               # Utility Classes
│   │   │   └── validator/                          # Custom Validators
│   │   │
│   │   └── resources/
│   │       ├── application.properties              # Application Configuration
│   │       ├── db/migration/                       # Flyway Database Migrations
│   │       │   ├── V1__init_schema.sql             # Initial schema creation
│   │       │   ├── V2__sp_create_organization.sql  # Org stored procedure
│   │       │   ├── V3__sp_create_role.sql          # Role stored procedure
│   │       │   ├── V4__sp_create_user.sql          # User stored procedure
│   │       │   └── V5__add_description_to_roles.sql # Add description column
│   │       ├── static/                             # Static assets (CSS, JS)
│   │       └── templates/                          # Email/view templates
│   │
│   └── test/
│       └── java/com/priyanshu/ems_ai/
│           └── EmsAiApplicationTests.java          # Integration tests
│
├── target/                                         # Build output (auto-generated)
├── pom.xml                                         # Maven configuration
├── mvnw                                            # Maven wrapper script (Linux/Mac)
├── mvnw.cmd                                        # Maven wrapper script (Windows)
└── README.md                                       # This file
```

---

## Configuration

### application.properties

Located at: `src/main/resources/application.properties`

```properties
# Application Name
spring.application.name=ems-ai

# Server Configuration
server.port=8081                    # API runs on http://localhost:8081

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/ems_ai
spring.datasource.username=postgres
spring.datasource.password=Demo@123

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=validate      # Only validates, doesn't modify schema
spring.jpa.show-sql=true                    # Print SQL queries to console
spring.jpa.properties.hibernate.format_sql=true # Format SQL for readability

# Flyway Database Migration
spring.flyway.enabled=true                  # Enable Flyway
spring.flyway.baseline-on-migrate=true      # Baseline if migrations exist
spring.flyway.locations=classpath:db/migration  # Migration scripts location
```

### Key Configuration Points

| Setting | Value | Purpose |
|---------|-------|---------|
| `server.port` | 8081 | Port where API listens |
| `spring.jpa.hibernate.ddl-auto` | validate | Prevents accidental schema changes |
| `spring.flyway.enabled` | true | Auto-runs database migrations |
| `spring.datasource.url` | jdbc:postgresql://localhost:5433/ems_ai | Database connection |

---

## Security

### Password Security

Passwords are hashed using **BCrypt** (Spring Security's `PasswordEncoder`):

```java
// In UserService.createUser()
user.setPassword(passwordEncoder.encode(request.getPassword()));
```

**Benefits:**
- Irreversible hashing (one-way)
- Salted to prevent rainbow table attacks
- Computationally expensive (resistant to brute-force)
- Industry standard for password storage

### Example Password Hash

```
Plain text:  SecurePassword@123
Hashed:      $2a$10$dXJ3SW6G7P50eS3xBNqr3OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW
```

### Data Validation

```
CreateUserRequest Validation:
├─ @NotBlank name          → Must be non-empty string
├─ @Email email            → Must be valid email format
├─ @NotBlank password      → Must be non-empty string
└─ @NotNull roleId         → Must exist and be UUID

Service-Level Validation:
├─ Organization exists     → Throws RuntimeException if not found
├─ Role exists             → Throws RuntimeException if not found
├─ Role belongs to org     → Validates cross-org role assignment
└─ Email uniqueness        → Database constraint handles this
```

### Data Isolation

- Users only within their organization can be created
- Roles are organization-specific
- Composite unique index on `(email, org_id)` allows same email in different orgs

---

## Troubleshooting

### Problem: "Connection Refused"

**Error:**
```
org.postgresql.util.PSQLException: Connection to localhost:5433 refused
```

**Solution:**
1. Verify PostgreSQL is running
2. Check database credentials in `application.properties`
3. Ensure database `ems_ai` exists
4. Verify port 5433 is correct (default PostgreSQL is 5432)

```bash
# Check if PostgreSQL is running
psql -U postgres -h localhost -p 5433
```

---

### Problem: "Schema Validation Failed"

**Error:**
```
Schema-validation: missing column [description] in table [ems.roles]
```

**Solution:**
This happens when entity classes don't match the database schema. The migration V5 adds the `description` column.

Run migrations:
```bash
mvn flyway:migrate
```

Or restart the application (migrations run automatically):
```bash
mvn clean spring-boot:run
```

---

### Problem: "Organization not found" when creating user

**Solution:**
1. Ensure the organization exists first:
   ```bash
   POST /api/organizations/createOrganization
   ```
   
2. Copy the returned UUID

3. Use that UUID when creating users:
   ```bash
   POST /organizations/{returned-uuid}/users
   ```

---

### Problem: "Role does not belong to organization"

**Solution:**
The role's `org_id` must match the organization you're creating the user in.

```sql
-- Check role's organization
SELECT id, name, org_id FROM ems.roles WHERE id = 'role-uuid';

-- The org_id should match the organization you're creating the user in
```

---

### Problem: Build fails with "Cannot find symbol: class UUID"

**Solution:**
This was a missing import issue. Ensure `UserService.java` has:

```java
import java.util.UUID;
import java.util.List;
```

---

### Problem: Application exits immediately with exit code 1

**Possible Causes:**
1. Database connection failed
2. Schema validation errors
3. Port 8081 already in use

**Debugging:**
```bash
# Run with more verbose output
mvn spring-boot:run -X

# Check if port is in use
netstat -ano | findstr :8081

# Kill process using port (Windows)
taskkill /PID <pid> /F
```

---

## Email Uniqueness

Email must be unique **within an organization**, but can be reused across organizations:

```sql
-- This is allowed (same email, different orgs)
INSERT INTO ems.users VALUES (
  gen_random_uuid(), 
  'org1-id', 
  'role-id', 
  'John', 
  'john@example.com', 
  'hashed_pwd', 
  true
);

INSERT INTO ems.users VALUES (
  gen_random_uuid(), 
  'org2-id',  -- Different org
  'role-id', 
  'John', 
  'john@example.com',  -- Same email is OK
  'hashed_pwd', 
  true
);

-- This will fail (same email, same org)
INSERT INTO ems.users VALUES (
  gen_random_uuid(), 
  'org1-id',  -- Same org as first
  'role-id', 
  'John Duplicate', 
  'john@example.com',  -- Duplicate in same org
  'hashed_pwd', 
  true
);
-- ERROR: duplicate key value violates unique constraint "idx_users_email_org"
```

---

## Working with Migrations

### Understanding Migration Files

Migrations are versioned and executed in order:

```
V1__init_schema.sql              (Initially created schema)
V2__sp_create_organization.sql   (Added org stored procedure)
V3__sp_create_role.sql           (Added role stored procedure)
V4__sp_create_user.sql           (Added user stored procedure)
V5__add_description_to_roles.sql (Added description column)
```

### Creating New Migrations

To add a new migration:

1. Create file: `src/main/resources/db/migration/V6__your_change.sql`
2. Write SQL:
   ```sql
   -- Add column
   ALTER TABLE ems.users ADD COLUMN department VARCHAR(100);
   ```
3. Restart application (Flyway runs automatically)

**Important:** Never modify existing migration files. Always create new versions.

---

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmsAiApplicationTests

# Run with coverage
mvn test jacoco:report
```

---

## Production Checklist

Before deploying to production:

- [ ] Change default PostgreSQL password
- [ ] Use environment variables for sensitive data (not in properties file)
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate` (already set)
- [ ] Enable HTTPS
- [ ] Implement proper exception handling and logging
- [ ] Add authentication (JWT, OAuth2, etc.)
- [ ] Implement rate limiting
- [ ] Add API documentation (Swagger/OpenAPI)
- [ ] Set up monitoring and alerting
- [ ] Configure backup strategy for database
- [ ] Implement audit logging for user actions

---

## Contributing

### Code Style Guide

1. **Follow Spring conventions:**
   - Classes: PascalCase (e.g., `UserService`)
   - Methods/Variables: camelCase (e.g., `createUser`)
   - Constants: UPPER_SNAKE_CASE (e.g., `MAX_USERS`)

2. **Use Lombok annotations:**
   - `@Data` - Getters, setters, equals, hashCode, toString
   - `@RequiredArgsConstructor` - Constructor for final fields
   - `@NoArgsConstructor` - Empty constructor
   - `@AllArgsConstructor` - Constructor for all fields

3. **Validate all inputs:**
   - Use `@Valid` on request bodies
   - Use validation annotations (`@NotNull`, `@Email`, etc.)
   - Add business logic validation in services

4. **Write meaningful commit messages:**
   ```
   feat: Add user password reset functionality
   fix: Resolve email validation bug
   docs: Update API documentation
   chore: Update dependencies
   ```

---

## Useful Commands

### Build Commands

```bash
# Full clean build
mvn clean install

# Skip tests during build
mvn clean install -DskipTests

# Build only (no install)
mvn clean compile
```

### Running Commands

```bash
# Run application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"

# Run with debug mode
mvn spring-boot:run -X
```

### Database Commands

```bash
# Run migrations
mvn flyway:migrate

# Validate migrations
mvn flyway:validate

# Info about migrations
mvn flyway:info
```

### Dependency Commands

```bash
# Tree of dependencies
mvn dependency:tree

# Check for updates
mvn versions:display-dependency-updates

# Identify vulnerabilities
mvn org.owasp:dependency-check-maven:check
```

---

## Glossary

| Term | Definition |
|------|-----------|
| **DTO** | Data Transfer Object - Used for API requests/responses |
| **Entity** | JPA annotated class representing database table |
| **Repository** | Data access layer interface extending JpaRepository |
| **Service** | Business logic layer containing domain operations |
| **Controller** | REST API endpoint handler |
| **BCrypt** | Password hashing algorithm |
| **UUID** | Universally Unique Identifier (128-bit) |
| **JPA** | Jakarta Persistence API specification |
| **Hibernate** | JPA implementation for ORM |
| **Flyway** | Database migration tool |
| **Spring Security** | Authentication and authorization framework |

---

## Resources & References

- [Spring Boot Official Guide](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs)
- [Flyway Database Migrations](https://flywaydb.org/documentation)
- [Spring Security Guide](https://spring.io/guides/topicals/spring-security-architecture)
- [Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation)
- [Lombok Getting Started](https://projectlombok.org/getting-started)

---

## Changelog

### Version 0.0.1 (Current)
- ✅ Initial project setup with Spring Boot 3.2.5
- ✅ Organization management (CRUD operations)
- ✅ User management with password hashing
- ✅ Role-based user assignment
- ✅ PostgreSQL integration with Flyway migrations
- ✅ Input validation and error handling
- ✅ RESTful API endpoints

### Known Issues
- None currently identified

### Planned Features
- [ ] JWT authentication
- [ ] User deletion with cascade
- [ ] Organization update endpoints
- [ ] Advanced user search/filtering
- [ ] Activity logging
- [ ] Email verification
- [ ] Password reset functionality
- [ ] Two-factor authentication

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Support & Contact

For issues, questions, or contributions:

1. Check this README and troubleshooting section
2. Review existing GitHub issues
3. Create a new issue with detailed description
4. Contact: priyanshu@example.com

---

## Acknowledgments

- Spring Boot team for excellent framework
- PostgreSQL community
- Flyway for database versioning
- Lombok for reducing boilerplate

---

**Last Updated:** February 24, 2026
**Project Version:** 0.0.1-SNAPSHOT
**Java Version:** 17
**Spring Boot Version:** 3.2.5
