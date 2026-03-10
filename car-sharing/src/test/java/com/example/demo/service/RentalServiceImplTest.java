package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.exception.CarOutOfStockException;
import com.example.demo.exception.RentalAlreadyReturnedException;
import com.example.demo.mapper.RentalMapper;
import com.example.demo.model.Car;
import com.example.demo.model.Rental;
import com.example.demo.model.User;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        rental = new Rental();
        rental.setId(1L);
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalDate(LocalDate.now());
        rental.setExpectedReturnDate(LocalDate.now().plusDays(7));
    }

    @Test
    void createRental_carOutOfStock_shouldThrowException() {
        car.setInventory(0);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(CarOutOfStockException.class,
                () -> rentalService.createRental(1L, 1L));

        verify(rentalRepository, never()).save(any());
    }

    @Test
    void returnRental_alreadyReturned_shouldThrowException() {
        rental.setActualReturnDate(LocalDate.now());

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        assertThrows(RentalAlreadyReturnedException.class,
                () -> rentalService.returnRental(1L));

        verify(carRepository, never()).save(any());
    }

    @Test
    void returnRental_valid_shouldIncreaseInventory() {
        rental.setActualReturnDate(null);
        car.setInventory(0);

        RentalResponseDto dto = new RentalResponseDto();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalRepository.save(any())).thenReturn(rental);
        when(rentalMapper.toDto(rental)).thenReturn(dto);

        RentalResponseDto result = rentalService.returnRental(1L);

        assertEquals(1, car.getInventory());
        verify(carRepository).save(car);
        verify(notificationService).sendRentalReturned(dto);
        assertNotNull(result);
    }
}