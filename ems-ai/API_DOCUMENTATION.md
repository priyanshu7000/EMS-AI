# EMS-AI API Documentation

## Overview

This document provides comprehensive documentation for all REST API endpoints in the EMS-AI (Employee Management System) application. Includes request/response examples, error handling, and validation details.

---

## Base URL

```
http://localhost:8081
```

---

## Error Handling

### Standard Error Response Format

All errors are returned in a consistent JSON format:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: 550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2026-02-24T12:30:45.123456",
  "path": "/api/organizations/550e8400-e29b-41d4-a716-446655440000"
}
```

### Validation Error Response Format

```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Request validation failed",
  "path": "/api/organizations/createOrganization",
  "timestamp": "2026-02-24T12:30:45.123456",
  "details": {
    "name": "Organization name is required and cannot be blank"
  }
}
```

### HTTP Status Codes

| Status | Meaning | When Thrown |
|--------|---------|------------|
| 200 | OK | Request successful |
| 400 | Bad Request | Invalid input, validation failures |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Resource already exists (duplicate) |
| 500 | Internal Server Error | Unexpected server error |

---

## Organization Endpoints

### Create Organization

**Endpoint:**
```http
POST /api/organizations/createOrganization
Content-Type: application/json
```

**Request:**
```json
{
  "name": "Tech Innovations Inc"
}
```

**Validation Rules:**
- `name` - Required, non-empty string (max 255 characters)

**Success Response (200):**
```json
"550e8400-e29b-41d4-a716-446655440000"
```

**Error Examples:**

Missing name field:
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Request validation failed",
  "details": {
    "name": "Organization name is required and cannot be blank"
  }
}
```

---

### Get All Organizations

**Endpoint:**
```http
GET /api/organizations/getAllOrganizations
```

**Success Response (200):**
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

**Empty List Response (200):**
```json
[]
```

---

### Get Organization by ID

**Endpoint:**
```http
GET /api/organizations/getOrganizationById/{id}
```

**Path Parameters:**
- `id` (UUID) - Organization UUID (required)

**Example:**
```http
GET /api/organizations/getOrganizationById/550e8400-e29b-41d4-a716-446655440000
```

**Success Response (200):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Tech Innovations Inc"
}
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: 550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2026-02-24T12:30:45.123456",
  "path": "/api/organizations/getOrganizationById/550e8400-e29b-41d4-a716-446655440000"
}
```

---

### Update Organization

**Endpoint:**
```http
PUT /api/organizations/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (UUID) - Organization UUID (required)

**Request:**
```json
{
  "name": "Updated Company Name"
}
```

**Success Response (200):**
```json
"Organization updated successfully"
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: invalid-uuid"
}
```

---

### Delete Organization

**Endpoint:**
```http
DELETE /api/organizations/{id}
```

**Path Parameters:**
- `id` (UUID) - Organization UUID (required)

**Important:** Deleting an organization will cascade delete all associated roles and users.

**Success Response (200):**
```json
"Organization deleted successfully"
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: invalid-uuid"
}
```

---

## Role Endpoints

### Create Role

**Endpoint:**
```http
POST /organizations/{orgId}/roles
Content-Type: application/json
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)

**Request:**
```json
{
  "name": "Manager",
  "description": "Managerial role for team leads"
}
```

**Validation Rules:**
- `name` - Required, non-empty string (max 100 characters)
- `description` - Optional, max 500 characters

**Success Response (200):**
```json
"a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6"
```

**Error Response - Organization Not Found (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: invalid-uuid"
}
```

**Error Response - Validation Failed (400):**
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Request validation failed",
  "details": {
    "name": "Role name is required and cannot be blank"
  }
}
```

---

### Get Roles by Organization

**Endpoint:**
```http
GET /organizations/{orgId}/roles
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)

**Success Response (200):**
```json
[
  {
    "id": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
    "name": "Manager",
    "description": "Managerial role for team leads",
    "organization": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Tech Innovations Inc"
    }
  },
  {
    "id": "b2c3d4e5-f6a7-48h9-i0j1-k2l3m4n5o6p7",
    "name": "Developer",
    "description": "Software developer role",
    "organization": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Tech Innovations Inc"
    }
  }
]
```

---

### Get Role by ID

**Endpoint:**
```http
GET /organizations/{orgId}/roles/{id}
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - Role UUID (required)

**Success Response (200):**
```json
{
  "id": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "name": "Manager",
  "description": "Managerial role for team leads",
  "organization": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Tech Innovations Inc"
  }
}
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Role not found with id: invalid-uuid"
}
```

---

### Update Role

**Endpoint:**
```http
PUT /organizations/{orgId}/roles/{id}
Content-Type: application/json
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - Role UUID (required)

**Request:**
```json
{
  "name": "Senior Manager",
  "description": "Senior managerial role with additional responsibilities"
}
```

**Success Response (200):**
```json
"Role updated successfully"
```

---

### Delete Role

**Endpoint:**
```http
DELETE /organizations/{orgId}/roles/{id}
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - Role UUID (required)

**Success Response (200):**
```json
"Role deleted successfully"
```

---

## User Endpoints

### Create User

**Endpoint:**
```http
POST /organizations/{orgId}/users
Content-Type: application/json
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)

**Request:**
```json
{
  "name": "John Doe",
  "email": "john.doe@techinnov.com",
  "password": "SecurePassword@123",
  "roleId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6"
}
```

**Validation Rules:**
- `name` - Required, non-empty string
- `email` - Required, valid email format
- `password` - Required, non-empty string (minimum 8 characters recommended)
- `roleId` - Required, valid UUID of existing role

**Important:** 
- Password is automatically hashed with BCrypt before storage
- Email must be unique within the organization
- Role must belong to the specified organization

**Success Response (200):**
```json
"user-uuid-here"
```

**Error Response - Organization Not Found (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: invalid-uuid"
}
```

**Error Response - Role Not Found (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Role not found with id: invalid-uuid"
}
```

**Error Response - Role Doesn't Belong to Organization (400):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Role does not belong to the specified organization"
}
```

**Error Response - Validation Failed (400):**
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Request validation failed",
  "details": {
    "email": "Email should be a valid email address",
    "password": "Password is required and cannot be blank"
  }
}
```

---

### Get Users by Organization

**Endpoint:**
```http
GET /organizations/{orgId}/users
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)

**Success Response (200):**
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

**Note:** Password is never returned in responses for security reasons.

---

### Get User by ID

**Endpoint:**
```http
GET /organizations/{orgId}/users/{id}
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - User UUID (required)

**Success Response (200):**
```json
{
  "id": "user-uuid-1",
  "name": "John Doe",
  "email": "john.doe@techinnov.com",
  "isActive": true,
  "roleName": "Manager"
}
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: invalid-uuid"
}
```

---

### Update User

**Endpoint:**
```http
PUT /organizations/{orgId}/users/{id}
Content-Type: application/json
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - User UUID (required)

**Request (all fields optional):**
```json
{
  "name": "John Doe Updated",
  "email": "john.updated@techinnov.com",
  "password": "NewPassword@456",
  "roleId": "new-role-uuid"
}
```

**Note:** You can update any combination of fields. Only provided fields will be updated.

**Success Response (200):**
```json
"User updated successfully"
```

**Error Response - New Role Not in Organization (400):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Role does not belong to the user's organization"
}
```

---

### Toggle User Status

**Endpoint:**
```http
PATCH /organizations/{orgId}/users/{id}/toggle-status
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - User UUID (required)

**Description:** Toggle user between active and inactive status.

**Success Response (200):**
```json
"User status toggled successfully"
```

**Error Response (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: invalid-uuid"
}
```

---

### Delete User

**Endpoint:**
```http
DELETE /organizations/{orgId}/users/{id}
```

**Path Parameters:**
- `orgId` (UUID) - Organization UUID (required)
- `id` (UUID) - User UUID (required)

**Success Response (200):**
```json
"User deleted successfully"
```

---

## Exception Handling Guide

### NotFoundException (404)
Thrown when a resource is not found in the database.

**Examples:**
- Organization doesn't exist
- Role doesn't exist
- User doesn't exist

**Solution:** Verify the UUID exists and is correctly spelled.

---

### BadRequestException (400)
Thrown when request data is invalid or business logic validation fails.

**Examples:**
- Empty organization name
- Role doesn't belong to organization
- Invalid email format

**Solution:** Check the `details` field in error response for specific field errors.

---

### ConflictException (409)
Thrown when trying to create a resource that already exists.

**Examples:**
- Email already exists in organization

**Solution:** Use a different value or update the existing resource.

---

### Validation Errors (400)
Automatically triggered by `@Valid` annotation on request bodies.

**Includes:**
- Field name
- Validation failure reason

**Example:**
```json
{
  "details": {
    "email": "Email should be a valid email address",
    "name": "Name is required and cannot be blank"
  }
}
```

---

## Security Considerations

### Password Handling
- Passwords are **never** returned in API responses
- Passwords are hashed with **BCrypt** before storage
- Passwords are salted to prevent rainbow table attacks
- Use HTTPS in production to encrypt passwords in transit

### Data Isolation
- Users can only belong to one organization
- Roles are organization-specific
- Email uniqueness is enforced per organization (same email can exist in different orgs)

### Email Validation
- Email format is validated using standard email regex
- Email must be unique within an organization
- Email is case-insensitive for storage

---

## Best Practices

### 1. Error Handling
Always check the HTTP status code before processing the response:
```javascript
if (response.status === 200) {
  // Process success
} else {
  // Read error.message and error.details
}
```

### 2. Resource Creation Flow
1. Create Organization first
2. Create Roles for that Organization
3. Create Users and assign them to Roles

### 3. Pagination Consideration
Current API returns all results. For large datasets, implement pagination:
```http
GET /api/organizations?page=0&size=20
```

### 4. Request Validation
All DTOs use Jakarta Validation annotations. Validation happens automatically.

---

## cURL Examples

### Create Organization
```bash
curl -X POST http://localhost:8081/api/organizations/createOrganization \
  -H "Content-Type: application/json" \
  -d '{"name": "Tech Innovations Inc"}'
```

### Get All Organizations
```bash
curl http://localhost:8081/api/organizations/getAllOrganizations
```

### Create Role
```bash
curl -X POST http://localhost:8081/organizations/{orgId}/roles \
  -H "Content-Type: application/json" \
  -d '{"name": "Manager", "description": "Managerial role"}'
```

### Create User
```bash
curl -X POST http://localhost:8081/organizations/{orgId}/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "SecurePass123",
    "roleId": "{roleId}"
  }'
```

### Update User
```bash
curl -X PUT http://localhost:8081/organizations/{orgId}/users/{userId} \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe"}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8081/organizations/{orgId}/users/{userId}
```

---

## API Testing Tools

### Recommended Tools
- **Postman** - GUI-based API testing
- **Insomnia** - Alternative REST client
- **cURL** - Command-line tool
- **Thunder Client** - VS Code extension

### Import Collection
Import the Postman collection available in the project for quick testing.

---

**Last Updated:** February 24, 2026
**API Version:** 1.0
**Status**: Production Ready
