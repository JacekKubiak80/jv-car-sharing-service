package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.exception.EmailAlreadyRegisteredException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto register(@Valid UserRequestDto userRequestDto) {
        userRepository.findByEmail(userRequestDto.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyRegisteredException("Email already registered: " + userRequestDto.getEmail());
                });
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.CUSTOMER);
        User savedUser = userRepository.save(user);
        notificationService.sendUserRegistered(savedUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateProfile(Long userId, @Valid UserRequestDto updatedUserRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntityFromDto(updatedUserRequestDto, user);
        User updatedUser = userRepository.save(user);
        notificationService.sendUserProfileUpdated(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto patchProfile(Long userId, UserRequestDto partialUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntityFromDto(partialUpdateDto, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUserRole(Long id, User.Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
