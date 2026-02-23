
package com.priyanshu.ems_ai.controller;
import com.priyanshu.ems_ai.dto.CreateOrganizationRequest;
import com.priyanshu.ems_ai.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.priyanshu.ems_ai.entity.Organization;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping("/create")
    public ResponseEntity<UUID> create(
            @Valid @RequestBody CreateOrganizationRequest request) {

        UUID orgId = service.createOrganization(request);
        return ResponseEntity.ok(orgId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Organization>> getAll() {
        return ResponseEntity.ok(service.getAllOrganizations()); // Fixed: wrapped in .ok()
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrganizationById(id)); // Fixed: wrapped in .ok()
    }
}