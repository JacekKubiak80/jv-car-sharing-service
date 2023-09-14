package com.example.demo.mapper;


import com.example.demo.dto.CarResponse;
import com.example.demo.model.Car;

public class CarMapper {

    private CarMapper() {}

    public static CarResponse toDto(Car car) {
        if (car == null) {
            return null;
        }

        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .type(Car.CarType.valueOf(car.getType().name()))
                .dailyFee(car.getDailyFee())
                .inventory(car.getInventory())
                .build();
    }
}
