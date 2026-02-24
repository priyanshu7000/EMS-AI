package com.priyanshu.ems_ai.exception;

/**
 * Exception thrown when a resource already exists (conflict).
 * Maps to HTTP 409 Conflict status.
 */
public class ConflictException extends RuntimeException {
    
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a ConflictException for duplicate resources
     * @param resourceName Name of the resource
     * @param identifier The duplicate identifier (e.g., email)
     * @return ConflictException with formatted message
     */
    public static ConflictException duplicateResource(String resourceName, String identifier) {
        return new ConflictException(resourceName + " already exists with identifier: " + identifier);
    }
}
