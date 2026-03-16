package com.carsharing.app.service;

import com.carsharing.app.dto.UserRequestDto;
import com.carsharing.app.dto.UserResponseDto;
import com.carsharing.app.mapper.UserMapper;
import com.carsharing.app.model.User;
import com.carsharing.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void getByEmail_ShouldReturnUserDto_WhenUserExists() {

        User user = User.builder().email("test@test.com").build();
        UserResponseDto dto = UserResponseDto.builder().email("test@test.com").build();

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        UserResponseDto result = userService.getByEmail("test@test.com");

        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void register_ShouldSaveUserAndSendNotification() {

        UserRequestDto request = UserRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        User user = new User();
        user.setPassword(request.getPassword()); // <--- kluczowa zmiana

        User savedUser = User.builder().id(1L).email("test@test.com").build();
        UserResponseDto response = UserResponseDto.builder().id(1L).build();

        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword"); // <--- any zamiast anyString
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(response);

        UserResponseDto result = userService.register(request);

        assertEquals(1L, result.getId());
        verify(notificationService).sendUserRegistered(savedUser);
    }
}