package com.priyanshu.ems_ai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response object for API errors.
 * Sent to client when an exception occurs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private Integer status;                 // HTTP status code (e.g., 404, 400)
    private String error;                   // Error type (e.g., "Not Found")
    private String message;                 // Human-readable error message
    private String timestamp;               // When the error occurred (ISO-8601)
    private String path;                    // API endpoint that was called
    private Map<String, String> details;    // Additional error details (for validation errors)
    private String traceId;                 // Request trace ID for debugging

    /**
     * Create error response for validation failures with field details
     */
    public static ErrorResponse validationError(Integer status, String message, 
                                               String path, Map<String, String> fieldErrors) {
        return ErrorResponse.builder()
                .status(status)
                .error("Validation Error")
                .message(message)
                .path(path)
                .details(fieldErrors)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * Create simple error response
     */
    public static ErrorResponse error(Integer status, String error, 
                                     String message, String path) {
        return ErrorResponse.builder()
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
