package com.priyanshu.ems_ai.service;

import com.priyanshu.ems_ai.dto.CreateOrganizationRequest;
import com.priyanshu.ems_ai.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    public UUID createOrganization(CreateOrganizationRequest request) {
        return repository.createOrganization(request.getName());
    }
}