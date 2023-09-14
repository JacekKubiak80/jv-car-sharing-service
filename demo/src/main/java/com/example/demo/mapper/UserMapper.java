package com.example.demo.mapper;


import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(User.Role.valueOf(user.getRole().name()))
                .build();
    }
}
