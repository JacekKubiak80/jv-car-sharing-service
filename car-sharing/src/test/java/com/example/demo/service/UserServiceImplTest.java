package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

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
        User savedUser = User.builder().id(1L).email("test@test.com").build();
        UserResponseDto response = UserResponseDto.builder().id(1L).build();

        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(response);

        UserResponseDto result = userService.register(request);

        assertEquals(1L, result.getId());
        verify(notificationService).sendUserRegistered(savedUser);
    }
}