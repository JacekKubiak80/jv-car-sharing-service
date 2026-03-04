package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<Car> searchCars(String brand, Car.CarType type, Integer minInventory, BigDecimal maxDailyFee) {
        return carRepository.findAll().stream()
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> type == null || car.getType() == type)
                .filter(car -> minInventory == null || car.getInventory() >= minInventory)
                .filter(car -> maxDailyFee == null || car.getDailyFee().compareTo(maxDailyFee) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
    }

    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Long id, Car carDetails) {
        Car car = getCarById(id);
        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setType(carDetails.getType());
        car.setDailyFee(carDetails.getDailyFee());
        car.setInventory(carDetails.getInventory());
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }
}
