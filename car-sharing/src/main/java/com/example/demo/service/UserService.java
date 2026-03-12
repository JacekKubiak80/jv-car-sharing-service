package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.model.User;
import jakarta.validation.Valid;

public interface UserService {

    UserResponseDto getByEmail(String email);

    UserResponseDto getUserById(Long id);

    UserResponseDto register(@Valid UserRequestDto userRequestDto);

    UserResponseDto updateProfile(Long userId, @Valid UserRequestDto updatedUserRequestDto);

    UserResponseDto patchProfile(Long userId, UserRequestDto partialUpdateDto);

    UserResponseDto updateUserRole(Long id, User.Role role);
}

