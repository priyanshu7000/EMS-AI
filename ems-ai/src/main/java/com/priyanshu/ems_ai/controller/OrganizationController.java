
package com.priyanshu.ems_ai.controller;
import com.priyanshu.ems_ai.dto.CreateOrganizationRequest;
import com.priyanshu.ems_ai.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping
    public ResponseEntity<UUID> create(
            @Valid @RequestBody CreateOrganizationRequest request) {

        UUID orgId = service.createOrganization(request);
        return ResponseEntity.ok(orgId);
    }
}