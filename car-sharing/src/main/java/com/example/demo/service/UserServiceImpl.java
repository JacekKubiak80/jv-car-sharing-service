package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;

    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto register(@Valid UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setRole(User.Role.CUSTOMER);
        User savedUser = userRepository.save(user);
        notificationService.sendUserRegistered(savedUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto updateProfile(String email,
                                         @Valid UserRequestDto updatedUserRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntityFromDto(updatedUserRequestDto, user);
        User updatedUser = userRepository.save(user);
        notificationService.sendUserProfileUpdated(updatedUser);
        return userMapper.toDto(updatedUser);
    }
}
