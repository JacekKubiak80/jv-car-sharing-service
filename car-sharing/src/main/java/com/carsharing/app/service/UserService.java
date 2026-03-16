package com.carsharing.app.service;

import com.carsharing.app.dto.UserRequestDto;
import com.carsharing.app.dto.UserResponseDto;
import com.carsharing.app.model.User;
import jakarta.validation.Valid;

public interface UserService {

    UserResponseDto getByEmail(String email);

    UserResponseDto getUserById(Long id);

    UserResponseDto register(@Valid UserRequestDto userRequestDto);

    UserResponseDto updateProfile(Long userId, @Valid UserRequestDto updatedUserRequestDto);

    UserResponseDto patchProfile(Long userId, UserRequestDto partialUpdateDto);

    UserResponseDto updateUserRole(Long id, User.Role role);

    User getUserEntityByEmail(String email);


}

