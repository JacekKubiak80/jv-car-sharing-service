package com.carsharing.app.service;

import com.carsharing.app.dto.RentalResponseDto;
import com.carsharing.app.exception.AccessDeniedException;
import com.carsharing.app.exception.CarOutOfStockException;
import com.carsharing.app.exception.RentalAlreadyReturnedException;
import com.carsharing.app.mapper.RentalMapper;
import com.carsharing.app.model.Car;
import com.carsharing.app.model.Rental;
import com.carsharing.app.model.User;
import com.carsharing.app.repository.CarRepository;
import com.carsharing.app.repository.RentalRepository;
import com.carsharing.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private Car car;
    private User user;
    private Rental rental;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setInventory(1);

        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setRole(User.Role.CUSTOMER);

        rental = new Rental();
        rental.setId(1L);
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalDate(LocalDate.now());
        rental.setExpectedReturnDate(LocalDate.now().plusDays(7));
        rental.setActualReturnDate(null);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                new org.springframework.security.authentication.TestingAuthenticationToken(
                        user.getEmail(), null
                )
        );
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createRental_carOutOfStock_shouldThrowException() {
        car.setInventory(0);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        LocalDate returnDate = LocalDate.now().plusDays(3);

        assertThrows(CarOutOfStockException.class, () ->
                rentalService.createRental(1L, returnDate));
    }

    @Test
    void returnRental_alreadyReturned_shouldThrowException() {
        rental.setActualReturnDate(LocalDate.now());
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(RentalAlreadyReturnedException.class,
                () -> rentalService.returnRental(1L));

        verify(carRepository, never()).save(any());
    }

    @Test
    void returnRental_notOwnerOrManager_shouldThrowAccessDenied() {
        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setRole(User.Role.CUSTOMER);
        rental.setUser(otherUser);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(AccessDeniedException.class,
                () -> rentalService.returnRental(1L));

        verify(carRepository, never()).save(any());
    }

    @Test
    void returnRental_valid_shouldIncreaseInventory() {
        rental.setActualReturnDate(null);
        car.setInventory(0);

        RentalResponseDto dto = new RentalResponseDto();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(rentalRepository.save(any())).thenReturn(rental);
        when(rentalMapper.toDto(rental)).thenReturn(dto);

        RentalResponseDto result = rentalService.returnRental(1L);

        assertEquals(1, car.getInventory());
        verify(carRepository).save(car);
        verify(notificationService).sendRentalReturned(dto);
        assertNotNull(result);
    }
}
