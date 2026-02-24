package com.priyanshu.ems_ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.priyanshu.ems_ai.entity.Role;
import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    /**
     * Find all roles for a specific organization
     * @param orgId The organization ID
     * @return List of roles in the organization
     */
    List<Role> findByOrganizationId(UUID orgId);
}
