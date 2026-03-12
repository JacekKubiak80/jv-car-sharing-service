package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller", description = "Operations for user profile management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Operation(summary = "Get current logged-in user's profile")
    @GetMapping("/me")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getByEmail(user.getEmail());
    }

    @Operation(summary = "Update current logged-in user's profile")
    @PutMapping("/me")
    public UserResponseDto updateProfile(@Valid @RequestBody UserRequestDto updatedUserRequestDto,
                                         @AuthenticationPrincipal User user) {
        return userService.updateProfile(user.getId(), updatedUserRequestDto);
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
    public UserResponseDto updateUserRole(@PathVariable Long id, @RequestParam User.Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
        return userService.getUserById(id);
    }

    @Operation(summary = "Partially update current user's profile")
    @PatchMapping("/me")
    public UserResponseDto patchProfile(@RequestBody UserRequestDto partialUpdateDto,
                                        @AuthenticationPrincipal User user) {
        return userService.patchProfile(user.getId(), partialUpdateDto);
    }
}
