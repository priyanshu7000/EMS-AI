package com.priyanshu.ems_ai.service;

import com.priyanshu.ems_ai.dto.CreateOrganizationRequest;
import com.priyanshu.ems_ai.exception.BadRequestException;
import com.priyanshu.ems_ai.exception.NotFoundException;
import com.priyanshu.ems_ai.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.priyanshu.ems_ai.entity.Organization;
import java.util.List;                                                  
import java.util.UUID;

/**
 * Service layer for Organization operations.
 * Handles business logic for organization management with proper validation and exception handling.
 */
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    /**
     * Create a new organization
     * @param request The organization creation request
     * @return UUID of the created organization
     * @throws BadRequestException if organization name is invalid
     */
    public UUID createOrganization(CreateOrganizationRequest request) {
        // Validate organization name
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw BadRequestException.validationFailed("name", "Organization name cannot be empty");
        }
        
        return repository.createOrganization(request.getName().trim());
    }

    /**
     * Get all organizations
     * @return List of all organizations
     */
    public List<Organization> getAllOrganizations() {
        return repository.findAll();
    }

    /**
     * Get organization by ID
     * @param id The organization ID
     * @return Organization object
     * @throws NotFoundException if organization doesn't exist
     */
    public Organization getOrganizationById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Organization", id));
    }

    /**
     * Update an organization
     * @param id The organization ID
     * @param request The updated organization data
     * @throws NotFoundException if organization doesn't exist
     * @throws BadRequestException if validation fails
     */
    public void updateOrganization(UUID id, CreateOrganizationRequest request) {
        Organization org = repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Organization", id));

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw BadRequestException.validationFailed("name", "Organization name cannot be empty");
        }

        org.setName(request.getName().trim());
        repository.save(org);
    }

    /**
     * Delete an organization by ID
     * Note: This will cascade delete all associated roles and users
     * @param id The organization ID
     * @throws NotFoundException if organization doesn't exist
     */
    public void deleteOrganization(UUID id) {
        Organization org = repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("Organization", id));

        repository.delete(org);
    }
}