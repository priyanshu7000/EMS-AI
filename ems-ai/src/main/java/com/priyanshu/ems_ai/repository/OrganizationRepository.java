package com.priyanshu.ems_ai.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.priyanshu.ems_ai.entity.Organization;

import java.util.UUID;

public interface OrganizationRepository
        extends Repository<Organization, UUID> {

    @Query(value = "SELECT ems.sp_create_organization(:name)", nativeQuery = true)
    UUID createOrganization(@Param("name") String name);
}