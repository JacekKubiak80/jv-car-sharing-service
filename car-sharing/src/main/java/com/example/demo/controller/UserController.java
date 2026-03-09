package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "User Controller", description = "Operations for user profile management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get current logged-in user's profile")
    @GetMapping("/me")
    public UserResponseDto getCurrentUser(Principal principal) {
        return userService.getByEmail(principal.getName());
    }

    @Operation(summary = "Update current logged-in user's profile")
    @PutMapping("/me")
    public UserResponseDto updateProfile(@Valid @RequestBody UserRequestDto updatedUserRequestDto,
                                         Principal principal) {
        return userService.updateProfile(principal.getName(), updatedUserRequestDto);
    }

    @Operation(summary = "Get any user by ID (MANAGER only)")
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}