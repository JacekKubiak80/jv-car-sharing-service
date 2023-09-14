package com.example.demo.service;


import com.example.demo.exception.CarOutOfStockException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.specification.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID " + id));
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car existingCar = getCarById(id);
        existingCar.setModel(updatedCar.getModel());
        existingCar.setBrand(updatedCar.getBrand());
        existingCar.setType(updatedCar.getType());
        existingCar.setInventory(updatedCar.getInventory());
        existingCar.setDailyFee(updatedCar.getDailyFee());
        return carRepository.save(existingCar);
    }

    public void deleteCar(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }

    public void decreaseInventory(Long carId) {
        Car car = getCarById(carId);
        if (car.getInventory() <= 0) {
            throw new CarOutOfStockException("Car with ID " + carId + " is out of stock");
        }
        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);
    }

    public void increaseInventory(Long carId) {
        Car car = getCarById(carId);
        car.setInventory(car.getInventory() + 1);
        carRepository.save(car);
    }

    public List<Car> searchCars(String brand, Car.CarType type, Integer minInventory, BigDecimal maxDailyFee) {
        Specification<Car> spec = Specification.where(CarSpecification.hasBrand(brand))
                .and(CarSpecification.hasType(type))
                .and(CarSpecification.minInventory(minInventory != null ? minInventory : 0))
                .and(CarSpecification.maxDailyFee(maxDailyFee));

        return carRepository.findAll(spec);
    }
}
