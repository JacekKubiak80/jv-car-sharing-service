package com.carsharing.app.service;

import com.carsharing.app.dto.CarRequestDto;
import com.carsharing.app.dto.CarResponseDto;
import com.carsharing.app.exception.ResourceNotFoundException;
import com.carsharing.app.mapper.CarMapper;
import com.carsharing.app.model.Car;
import com.carsharing.app.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CarResponseDto> searchCars(String brand,
                                           Car.CarType type,
                                           Integer minInventory,
                                           java.math.BigDecimal maxDailyFee,
                                           Pageable pageable) {



        Page<Car> pageResult = carRepository.findAll(pageable);

        List<CarResponseDto> filtered = pageResult.stream()
                .filter(Objects::nonNull)
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> type == null || car.getType() == type)
                .filter(car -> minInventory == null || car.getInventory() >= minInventory)
                .filter(car ->
                        maxDailyFee == null || car.getDailyFee().compareTo(maxDailyFee) <= 0)
                .map(carMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(filtered, pageable, pageResult.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public CarResponseDto createCar(CarRequestDto request) {
        Car car = Car.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .type(parseCarType(String.valueOf(request.getType())))
                .inventory(request.getInventory())
                .dailyFee(request.getDailyFee())
                .build();
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarResponseDto updateCar(Long id, CarRequestDto request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setType(request.getType());
        car.setInventory(request.getInventory());
        car.setDailyFee(request.getDailyFee());

        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }


    private Car.CarType parseCarType(String type) {
        if (type == null) return null;
        try {
            return Car.CarType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Invalid car type: " + type);
        }
    }
}
