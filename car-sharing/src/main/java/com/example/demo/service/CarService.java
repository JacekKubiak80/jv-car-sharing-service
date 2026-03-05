package com.example.demo.service;

import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface CarService {

    Page<CarResponseDto> searchCars(String brand, Car.CarType type, Integer minInventory,
                                    BigDecimal maxDailyFee, Pageable pageable);

    CarResponseDto getCarById(Long id);

    CarResponseDto createCar(CarRequestDto carRequestDto);

    CarResponseDto updateCar(Long id, CarRequestDto carRequestDto);

    void deleteCar(Long id);
}
