package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller", description =
        "Operations for user registration and profile management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.register(userRequestDto);
    }

    @Operation(summary = "Get current logged-in user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/me")
    public UserResponseDto getCurrentUser(Principal principal) {
        return userService.getByEmail(principal.getName());
    }

    @Operation(summary = "Update current logged-in user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PutMapping("/me")
    public UserResponseDto updateProfile(@Valid @RequestBody UserRequestDto updatedUserRequestDto,
                                         Principal principal) {
        return userService.updateProfile(principal.getName(), updatedUserRequestDto);
    }

    @Operation(summary = "Get any user by ID (MANAGER only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-manager users"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
