package com.example.demo.service;

import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public Page<CarResponseDto> searchCars(String brand, Car.CarType type,
                                           Integer minInventory, BigDecimal maxDailyFee,
                                           Pageable pageable) {
        List<CarResponseDto> filtered = carRepository.findAll().stream()
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> type == null || car.getType() == type)
                .filter(car -> minInventory == null || car.getInventory() >= minInventory)
                .filter(car -> maxDailyFee == null || car.getDailyFee().compareTo(maxDailyFee) <= 0)
                .map(carMapper::toDto)
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<CarResponseDto> pageContent = filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    public CarResponseDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return carMapper.toDto(car);
    }

    @Override
    public CarResponseDto createCar(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        Car saved = carRepository.save(car);
        return carMapper.toDto(saved);
    }

    @Override
    public CarResponseDto updateCar(Long id, CarRequestDto carRequestDto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        carMapper.updateEntityFromDto(carRequestDto, car);
        Car updated = carRepository.save(car);
        return carMapper.toDto(updated);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        carRepository.delete(car);
    }
}
