package com.priyanshu.ems_ai.exception;

/**
 * Exception thrown when a requested resource is not found in the database.
 * Maps to HTTP 404 Not Found status.
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a NotFoundException for a specific entity type and ID
     * @param entityName Name of the entity (e.g., "Organization", "User")
     * @param id The ID that was searched for
     * @return NotFoundException with formatted message
     */
    public static NotFoundException ofId(String entityName, Object id) {
        return new NotFoundException(entityName + " not found with id: " + id);
    }
}
