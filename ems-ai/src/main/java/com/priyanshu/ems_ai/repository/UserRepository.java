package com.priyanshu.ems_ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.priyanshu.ems_ai.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByOrganizationId(UUID orgId);
}
