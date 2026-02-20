package com.priyanshu.ems_ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrganizationRequest {

    @NotBlank
    private String name;
}