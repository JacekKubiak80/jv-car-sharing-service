package com.carsharing.app.service;

import com.carsharing.app.dto.CarRequestDto;
import com.carsharing.app.dto.CarResponseDto;
import com.carsharing.app.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

public interface CarService {

    Page<CarResponseDto> searchCars(String brand,
                                    Car.CarType type,
                                    Integer minInventory,
                                    BigDecimal maxDailyFee,
                                    Pageable pageable);

    CarResponseDto getCarById(Long id);

    CarResponseDto createCar(CarRequestDto request);

    CarResponseDto updateCar(Long id, CarRequestDto request);

    void deleteCar(Long id);
}
