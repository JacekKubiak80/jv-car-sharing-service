package com.example.demo.service;


import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void getCarById_ShouldReturnCarDto_WhenCarExists() {

        Car car = Car.builder().id(1L).brand("Toyota").build();
        CarResponseDto dto = CarResponseDto.builder().id(1L).brand("Toyota").build();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(dto);

        CarResponseDto result = carService.getCarById(1L);

        assertEquals("Toyota", result.getBrand());
    }

    @Test
    void createCar_ShouldSaveAndReturnCarDto() {

        CarRequestDto request = CarRequestDto.builder()
                .brand("BMW")
                .model("X5")
                .type(Car.CarType.SUV)
                .inventory(5)
                .dailyFee(BigDecimal.valueOf(200))
                .build();

        Car car = new Car();
        Car savedCar = Car.builder().id(1L).build();
        CarResponseDto responseDto = CarResponseDto.builder().id(1L).build();

        when(carMapper.toEntity(request)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(savedCar);
        when(carMapper.toDto(savedCar)).thenReturn(responseDto);

        CarResponseDto result = carService.createCar(request);

        assertEquals(1L, result.getId());
    }

    @Test
    void deleteCar_ShouldDeleteCar_WhenExists() {

        Car car = Car.builder().id(1L).build();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        carService.deleteCar(1L);

        verify(carRepository).delete(car);
    }
}