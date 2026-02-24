package com.priyanshu.ems_ai.repository;

import com.priyanshu.ems_ai.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    @Query(value = "SELECT ems.sp_create_organization(:name)", nativeQuery = true)
    UUID createOrganization(@Param("name") String name);
    
    // Note: findAll() and findById() are now inherited automatically!
}