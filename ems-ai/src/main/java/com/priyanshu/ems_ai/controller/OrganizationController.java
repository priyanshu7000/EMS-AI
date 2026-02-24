
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

/**
 * REST controller for Organization endpoints.
 * Provides API operations for managing organizations.
 */
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    /**
     * Create a new organization
     * @param request Organization creation request
     * @return UUID of created organization
     */
    @PostMapping("/createOrganization")
    public ResponseEntity<UUID> create(
            @Valid @RequestBody CreateOrganizationRequest request) {
        UUID orgId = service.createOrganization(request);
        return ResponseEntity.ok(orgId);
    }

    /**
     * Get all organizations
     * @return List of all organizations
     */
    @GetMapping("/getAllOrganizations")
    public ResponseEntity<List<Organization>> getAll() {
        return ResponseEntity.ok(service.getAllOrganizations());
    }

    /**
     * Get organization by ID
     * @param id Organization UUID
     * @return Organization details
     */
    @GetMapping("/getOrganizationById/{id}")
    public ResponseEntity<Organization> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrganizationById(id));
    }

    /**
     * Update an existing organization
     * @param id Organization UUID
     * @param request Updated organization data
     * @return Success response
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrganization(
            @PathVariable UUID id,
            @Valid @RequestBody CreateOrganizationRequest request) {
        service.updateOrganization(id, request);
        return ResponseEntity.ok("Organization updated successfully");
    }

    /**
     * Delete an organization (cascades to roles and users)
     * @param id Organization UUID
     * @return Success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable UUID id) {
        service.deleteOrganization(id);
        return ResponseEntity.ok("Organization deleted successfully");
    }
}