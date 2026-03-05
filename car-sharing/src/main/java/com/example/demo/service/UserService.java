package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import jakarta.validation.Valid;

public interface UserService {

    UserResponseDto getByEmail(String email);

    UserResponseDto getUserById(Long id);

    UserResponseDto register(@Valid UserRequestDto userRequestDto);

    UserResponseDto updateProfile(String email, UserRequestDto updatedUserRequestDto);
}
