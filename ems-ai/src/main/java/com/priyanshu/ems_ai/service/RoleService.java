package com.priyanshu.ems_ai.service;

import com.priyanshu.ems_ai.dto.CreateRoleRequest;
import com.priyanshu.ems_ai.entity.Organization;
import com.priyanshu.ems_ai.entity.Role;
import com.priyanshu.ems_ai.exception.BadRequestException;
import com.priyanshu.ems_ai.exception.ConflictException;
import com.priyanshu.ems_ai.exception.NotFoundException;
import com.priyanshu.ems_ai.repository.OrganizationRepository;
import com.priyanshu.ems_ai.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service layer for Role operations.
 * Handles business logic for role management with proper validation and exception handling.
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;

    /**
     * Create a new role for an organization
     * @param orgId The organization ID
     * @param request The role creation request
     * @return UUID of the created role
     * @throws NotFoundException if organization doesn't exist
     * @throws ConflictException if role name already exists in organization
     * @throws BadRequestException if input validation fails
     */
    public UUID createRole(UUID orgId, CreateRoleRequest request) {
        // Validate organization exists
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> NotFoundException.ofId("Organization", orgId));

        // Validate role name
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw BadRequestException.validationFailed("name", "Role name cannot be empty");
        }

        // Create and save role
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName(request.getName().trim());
        role.setDescription(request.getDescription());
        role.setOrganization(org);

        return roleRepository.save(role).getId();
    }

    /**
     * Get all roles for a specific organization
     * @param orgId The organization ID
     * @return List of roles
     * @throws NotFoundException if organization doesn't exist
     */
    public List<Role> getRolesByOrganizationId(UUID orgId) {
        // Validate organization exists
        organizationRepository.findById(orgId)
                .orElseThrow(() -> NotFoundException.ofId("Organization", orgId));

        return roleRepository.findByOrganizationId(orgId);
    }

    /**
     * Get a specific role by ID
     * @param id The role ID
     * @return Role object
     * @throws NotFoundException if role doesn't exist
     */
    public Role getRoleById(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Role", id));
    }

    /**
     * Update an existing role
     * @param id The role ID
     * @param request The updated role data
     * @throws NotFoundException if role doesn't exist
     * @throws BadRequestException if validation fails
     */
    public void updateRole(UUID id, CreateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Role", id));

        // Validate and update name
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            role.setName(request.getName().trim());
        } else {
            throw BadRequestException.validationFailed("name", "Role name cannot be empty");
        }

        // Update description if provided
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        roleRepository.save(role);
    }

    /**
     * Delete a role by ID
     * @param id The role ID
     * @throws NotFoundException if role doesn't exist
     */
    public void deleteRole(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Role", id));

        roleRepository.delete(role);
    }
}
