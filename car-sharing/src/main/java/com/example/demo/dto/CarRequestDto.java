package com.example.demo.dto;

import com.example.demo.model.Car;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequestDto {

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotNull(message = "Car type cannot be null")
    private Car.CarType type;

    @Min(value = 0, message = "Inventory cannot be negative")
    private int inventory;

    @NotNull(message = "Daily fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily fee must be greater than 0")
    private BigDecimal dailyFee;
}
