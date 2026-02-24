package com.priyanshu.ems_ai.controller;

import com.priyanshu.ems_ai.dto.CreateUserRequest;
import com.priyanshu.ems_ai.dto.UserResponse;
import com.priyanshu.ems_ai.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for User endpoints.
 * Provides API operations for managing users within organizations.
 */
@RestController
@RequestMapping("/organizations/{orgId}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Create a new user in an organization with role assignment
     * @param orgId Organization UUID
     * @param request User creation request
     * @return UUID of created user
     */
    @PostMapping
    public ResponseEntity<UUID> createUser(
            @PathVariable UUID orgId,
            @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(orgId, request));
    }

    /**
     * Get all users in an organization
     * @param orgId Organization UUID
     * @return List of users in the organization
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(
            @PathVariable UUID orgId) {
        return ResponseEntity.ok(userService.getUsersByOrganization(orgId));
    }

    /**
     * Get a specific user by ID
     * @param id User UUID
     * @return User details
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Update an existing user
     * @param orgId Organization UUID
     * @param id User UUID
     * @param request Updated user data
     * @return Success response
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable UUID orgId,
            @PathVariable UUID id,
            @Valid @RequestBody CreateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok("User updated successfully");
    }

    /**
     * Toggle user active/inactive status
     * @param orgId Organization UUID
     * @param id User UUID
     * @return Success response
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<String> toggleUserStatus(
            @PathVariable UUID orgId,
            @PathVariable UUID id) {
        userService.toggleUserStatus(id);
        return ResponseEntity.ok("User status toggled successfully");
    }

    /**
     * Delete a user
     * @param orgId Organization UUID
     * @param id User UUID
     * @return Success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable UUID orgId,
            @PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}