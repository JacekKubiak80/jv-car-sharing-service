package com.example.demo.service;

import com.example.demo.model.Car;
import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    List<Car> searchCars(String brand, Car.CarType type, Integer minInventory,
                         BigDecimal maxDailyFee);

    Car getCarById(Long id);

    Car createCar(Car car);

    Car updateCar(Long id, Car car);

    void deleteCar(Long id);
}
