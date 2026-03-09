package com.example.demo.service;


import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface CarService {

    Page<CarResponseDto> searchCars(String brand,
                                    String type,
                                    Integer minInventory,
                                    BigDecimal maxDailyFee,
                                    Pageable pageable);

    CarResponseDto getCarById(Long id);

    CarResponseDto createCar(CarRequestDto request);

    CarResponseDto updateCar(Long id, CarRequestDto request);

    void deleteCar(Long id);
}
