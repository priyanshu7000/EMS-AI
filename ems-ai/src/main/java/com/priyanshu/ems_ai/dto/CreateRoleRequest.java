package com.priyanshu.ems_ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a role.
 * Includes validation constraints to ensure data quality.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequest {

    @NotBlank(message = "Role name is required and cannot be blank")
    private String name;

    private String description;  // Optional field
}
