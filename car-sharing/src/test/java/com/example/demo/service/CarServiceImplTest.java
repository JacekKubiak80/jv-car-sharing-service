package com.example.demo.service;

import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private CarRequestDto requestDto;
    private CarResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        car = Car.builder()
                .id(1L)
                .brand("Toyota")
                .model("Corolla")
                .type(Car.CarType.SEDAN)
                .inventory(5)
                .dailyFee(new BigDecimal("100.00"))
                .build();

        requestDto = CarRequestDto.builder()
                .brand("Toyota")
                .model("Corolla")
                .type(Car.CarType.SEDAN)
                .inventory(5)
                .dailyFee(new BigDecimal("100.00"))
                .build();

        responseDto = CarResponseDto.builder()
                .id(1L)
                .brand("Toyota")
                .model("Corolla")
                .type(Car.CarType.SEDAN)
                .inventory(5)
                .dailyFee(new BigDecimal("100.00"))
                .build();
    }

    @Test
    void testGetCarById_success() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(responseDto);

        CarResponseDto result = carService.getCarById(1L);

        assertEquals(responseDto, result);
        verify(carRepository).findById(1L);
    }

    @Test
    void testGetCarById_notFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> carService.getCarById(1L));
        assertEquals("Car not found with id: 1", ex.getMessage());
    }

    @Test
    void testCreateCar_success() {
        when(carRepository.save(any(Car.class))).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(responseDto);

        CarResponseDto result = carService.createCar(requestDto);

        assertEquals(responseDto, result);
        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testUpdateCar_success() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(responseDto);

        CarResponseDto result = carService.updateCar(1L, requestDto);

        assertEquals(responseDto, result);
        verify(carRepository).save(car);
    }

    @Test
    void testUpdateCar_notFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> carService.updateCar(1L, requestDto));
        assertEquals("Car not found with id: 1", ex.getMessage());
    }

    @Test
    void testDeleteCar_success() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        carService.deleteCar(1L);

        verify(carRepository).delete(car);
    }

    @Test
    void testDeleteCar_notFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> carService.deleteCar(1L));
        assertEquals("Car not found with id: 1", ex.getMessage());
    }

    @Test
    void testSearchCars_filtersApplied() {
        Page<Car> page = new PageImpl<>(List.of(car));
        when(carRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(carMapper.toDto(car)).thenReturn(responseDto);

        Page<CarResponseDto> result = carService.searchCars("Toyota", "SEDAN", 1, new BigDecimal("200"), Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals(responseDto, result.getContent().get(0));
    }

    @Test
    void testSearchCars_invalidType_throwsException() {
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> carService.searchCars("Toyota", "INVALID", null, null, Pageable.unpaged()));
        assertTrue(ex.getMessage().contains("Invalid car type"));
    }
}