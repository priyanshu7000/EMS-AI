package com.priyanshu.ems_ai.controller;

import com.priyanshu.ems_ai.dto.CreateRoleRequest;
import com.priyanshu.ems_ai.entity.Role;
import com.priyanshu.ems_ai.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Role endpoints.
 * Provides API operations for managing roles within organizations.
 */
@RestController
@RequestMapping("/organizations/{orgId}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * Create a new role in an organization
     * @param orgId Organization UUID
     * @param request Role creation request
     * @return UUID of created role
     */
    @PostMapping
    public ResponseEntity<UUID> createRole(
            @PathVariable UUID orgId,
            @Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(orgId, request));
    }

    /**
     * Get all roles in an organization
     * @param orgId Organization UUID
     * @return List of roles in the organization
     */
    @GetMapping
    public ResponseEntity<List<Role>> getRolesByOrganization(
            @PathVariable UUID orgId) {
        return ResponseEntity.ok(roleService.getRolesByOrganizationId(orgId));
    }

    /**
     * Get a specific role by ID
     * @param id Role UUID
     * @return Role details
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    /**
     * Update an existing role
     * @param orgId Organization UUID
     * @param id Role UUID
     * @param request Updated role data
     * @return Success response
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(
            @PathVariable UUID orgId,
            @PathVariable UUID id,
            @Valid @RequestBody CreateRoleRequest request) {
        roleService.updateRole(id, request);
        return ResponseEntity.ok("Role updated successfully");
    }

    /**
     * Delete a role
     * @param orgId Organization UUID
     * @param id Role UUID
     * @return Success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(
            @PathVariable UUID orgId,
            @PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
