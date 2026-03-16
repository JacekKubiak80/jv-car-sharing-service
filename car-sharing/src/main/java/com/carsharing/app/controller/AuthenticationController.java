package com.carsharing.app.controller;

import com.carsharing.app.dto.UserLoginRequestDto;
import com.carsharing.app.dto.UserLoginResponseDto;
import com.carsharing.app.dto.UserRequestDto;
import com.carsharing.app.dto.UserResponseDto;
import com.carsharing.app.model.User;
import com.carsharing.app.security.JwtTokenProvider;
import com.carsharing.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller", description = "User registration and login operations")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.register(userRequestDto);
    }

    @Operation(summary = "Login a user and return JWT token")
    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userService.getUserEntityByEmail(loginRequest.getEmail());
        String jwtToken = jwtTokenProvider.generateToken(user);
        return new UserLoginResponseDto(jwtToken);
    }
}
