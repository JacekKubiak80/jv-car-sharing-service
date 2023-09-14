package com.example.demo.dto;

import com.example.demo.model.Car;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequest {

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotNull(message = "Car type cannot be null")
    private Car.CarType type;

    @Min(value = 0, message = "Inventory cannot be negative")
    private int inventory;

    @NotNull(message = "Daily fee cannot be null")
    private BigDecimal dailyFee;
}
