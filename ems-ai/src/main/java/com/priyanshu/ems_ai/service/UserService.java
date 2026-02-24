package com.priyanshu.ems_ai.service;

import com.priyanshu.ems_ai.dto.CreateUserRequest;
import com.priyanshu.ems_ai.dto.UserResponse;
import com.priyanshu.ems_ai.exception.BadRequestException;
import com.priyanshu.ems_ai.exception.NotFoundException;
import com.priyanshu.ems_ai.repository.OrganizationRepository;
import com.priyanshu.ems_ai.repository.RoleRepository;
import com.priyanshu.ems_ai.repository.UserRepository;
import com.priyanshu.ems_ai.entity.Organization;
import com.priyanshu.ems_ai.entity.Role;
import com.priyanshu.ems_ai.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

/**
 * Service layer for User operations.
 * Handles business logic for user management with proper validation and exception handling.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a new user with role assignment
     * @param orgId The organization ID
     * @param request The user creation request
     * @return UUID of the created user
     * @throws NotFoundException if organization or role doesn't exist
     * @throws BadRequestException if role doesn't belong to organization
     */
    public UUID createUser(UUID orgId, CreateUserRequest request) {
        // Validate organization exists
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> NotFoundException.ofId("Organization", orgId));

        // Validate role exists
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> NotFoundException.ofId("Role", request.getRoleId()));

        // Validate role belongs to organization
        if (!role.getOrganization().getId().equals(orgId)) {
            throw new BadRequestException("Role does not belong to the specified organization");
        }

        // Create and save user
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setOrganization(org);
        user.setRole(role);

        userRepository.save(user);

        return user.getId();
    }

    /**
     * Get all users in an organization as response DTOs
     * @param orgId The organization ID
     * @return List of user response objects
     * @throws NotFoundException if organization doesn't exist
     */
    public List<UserResponse> getUsersByOrganization(UUID orgId) {
        // Validate organization exists
        organizationRepository.findById(orgId)
                .orElseThrow(() -> NotFoundException.ofId("Organization", orgId));

        return userRepository.findByOrganizationId(orgId)
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getIsActive(),
                        user.getRole().getName()
                ))
                .toList();
    }

    /**
     * Get a specific user by ID
     * @param id The user ID
     * @return User response object
     * @throws NotFoundException if user doesn't exist
     */
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("User", id));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getIsActive(),
                user.getRole().getName()
        );
    }

    /**
     * Update an existing user
     * @param id The user ID
     * @param request The updated user data
     * @throws NotFoundException if user doesn't exist
     * @throws BadRequestException if validation fails
     */
    public void updateUser(UUID id, CreateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("User", id));

        // Update name if provided
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName().trim());
        }

        // Update email if provided
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            user.setEmail(request.getEmail().trim());
        }

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update role if provided
        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> NotFoundException.ofId("Role", request.getRoleId()));

            // Validate role belongs to user's organization
            if (!role.getOrganization().getId().equals(user.getOrganization().getId())) {
                throw new BadRequestException("Role does not belong to the user's organization");
            }

            user.setRole(role);
        }

        userRepository.save(user);
    }

    /**
     * Toggle user active status
     * @param id The user ID
     * @throws NotFoundException if user doesn't exist
     */
    public void toggleUserStatus(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("User", id));

        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    /**
     * Delete a user by ID
     * @param id The user ID
     * @throws NotFoundException if user doesn't exist
     */
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> NotFoundException.ofId("User", id));

        userRepository.delete(user);
    }
}