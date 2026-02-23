package com.priyanshu.ems_ai.service;

import com.priyanshu.ems_ai.dto.CreateOrganizationRequest;
import com.priyanshu.ems_ai.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.priyanshu.ems_ai.entity.Organization;
import java.util.List;                                                  
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    public UUID createOrganization(CreateOrganizationRequest request) {
        return repository.createOrganization(request.getName());
    }

    public List<Organization> getAllOrganizations() {
        return repository.findAll();
    }

    public Organization getOrganizationById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }
}