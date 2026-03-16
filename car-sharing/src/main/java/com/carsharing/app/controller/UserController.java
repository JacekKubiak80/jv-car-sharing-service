package com.carsharing.app.controller;

import com.carsharing.app.dto.UserRequestDto;
import com.carsharing.app.dto.UserResponseDto;
import com.carsharing.app.model.User;
import com.carsharing.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "Operations for user profile management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get current logged-in user's profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @GetMapping("/me")
    public UserResponseDto getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getByEmail(email);
    }

    @Operation(summary = "Update current logged-in user's profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @PutMapping("/me")
    public UserResponseDto updateProfile(@Valid @RequestBody UserRequestDto updatedUserRequestDto,
                                         Authentication authentication) {
        String email = authentication.getName();
        Long userId = userService.getUserEntityByEmail(email).getId();
        return userService.updateProfile(userId, updatedUserRequestDto);
    }

    @Operation(summary = "Partially update current user's profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @PatchMapping("/me")
    public UserResponseDto patchProfile(@RequestBody UserRequestDto partialUpdateDto,
                                        Authentication authentication) {
        String email = authentication.getName();
        Long userId = userService.getUserEntityByEmail(email).getId();
        return userService.patchProfile(userId, partialUpdateDto);
    }

    @Operation(summary = "Get any user by ID (MANAGER only)")
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update user role (MANAGER only)")
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/role")
    public UserResponseDto updateUserRole(@PathVariable Long id,
                                          @RequestParam User.Role role) {
        return userService.updateUserRole(id, role);
    }
}