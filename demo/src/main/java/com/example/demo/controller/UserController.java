package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/me")
    public User getCurrentUser(@RequestParam String email) {
        return userService.getCurrentUser(email);
    }

    @PutMapping("/me")
    public User updateProfile(@RequestParam Long id, @RequestBody User updatedUser) {
        return userService.updateProfile(id, updatedUser);
    }

    @PutMapping("/{id}/role")
    public void updateRole(@PathVariable Long id, @RequestParam User.Role role) {
        userService.updateRole(id, role);
    }
}
