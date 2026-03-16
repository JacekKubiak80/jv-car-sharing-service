package com.carsharing.app.controller;

import com.carsharing.app.dto.CarRequestDto;
import com.carsharing.app.dto.CarResponseDto;
import com.carsharing.app.model.Car;
import com.carsharing.app.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Car management", description = "Endpoints for managing cars")
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get all cars with optional filters")
    @GetMapping
    public Page<CarResponseDto> getAllCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Car.CarType type,
            @RequestParam(required = false) Integer minInventory,
            @RequestParam(required = false) BigDecimal maxDailyFee,
            Pageable pageable
    ) {

        return carService.searchCars(brand, type, minInventory, maxDailyFee, pageable);
    }

    @Operation(summary = "Get a car by its ID")
    @GetMapping("/{id}")
    public CarResponseDto getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @Operation(summary = "Add a new car")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDto createCar(@Valid @RequestBody CarRequestDto request) {
        return carService.createCar(request);
    }

    @Operation(summary = "Update an existing car")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public CarResponseDto updateCar(
            @PathVariable Long id,
            @Valid @RequestBody CarRequestDto request
    ) {
        return carService.updateCar(id, request);
    }

    @Operation(summary = "Delete a car by its ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
