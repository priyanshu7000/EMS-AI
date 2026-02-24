package com.priyanshu.ems_ai.exception;

/**
 * Exception thrown when client request data is invalid.
 * Maps to HTTP 400 Bad Request status.
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a BadRequestException for validation failures
     * @param fieldName The field that failed validation
     * @param reason The reason for validation failure
     * @return BadRequestException with formatted message
     */
    public static BadRequestException validationFailed(String fieldName, String reason) {
        return new BadRequestException("Validation failed for field '" + fieldName + "': " + reason);
    }
}
