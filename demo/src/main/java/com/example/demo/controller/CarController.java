package com.example.demo.controller;

import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Car Controller", description = "Operations for managing cars")
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cars")
    })
    @GetMapping
    public List<CarResponseDto> getAllCars(
            @Parameter(description = "Filter by car brand", example = "Toyota") @RequestParam(required = false) String brand,
            @Parameter(description = "Filter by car type (SEDAN, SUV, HATCHBACK, UNIVERSAL)", example = "SUV") @RequestParam(required = false) Car.CarType type,
            @Parameter(description = "Filter by minimum inventory", example = "1") @RequestParam(required = false) Integer minInventory,
            @Parameter(description = "Filter by maximum daily fee", example = "100.00") @RequestParam(required = false) BigDecimal maxDailyFee
    ) {
        return carService.searchCars(brand, type, minInventory, maxDailyFee)
                .stream()
                .map(CarMapper.INSTANCE::toDto)
                .toList();
    }

    @Operation(summary = "Get car by ID", description = "Retrieve detailed information about a car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car found"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{id}")
    public CarResponseDto getCarById(
            @Parameter(description = "ID of the car to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return CarMapper.INSTANCE.toDto(carService.getCarById(id));
    }

    @Operation(summary = "Create a new car", description = "Create a new car in the system (MANAGER only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car successfully created"),
            @ApiResponse(responseCode = "403", description = "Forbidden, only MANAGER can create")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDto createCar(
            @Parameter(description = "Car data to create", required = true)
            @RequestBody CarRequestDto carRequestDto) {
        Car car = CarMapper.INSTANCE.toEntity(carRequestDto);
        return CarMapper.INSTANCE.toDto(carService.createCar(car));
    }

    @Operation(summary = "Update a car", description = "Update an existing car (MANAGER only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden, only MANAGER can update"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public CarResponseDto updateCar(
            @Parameter(description = "ID of the car to update", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated car data", required = true)
            @RequestBody CarRequestDto carRequestDto) {
        Car car = CarMapper.INSTANCE.toEntity(carRequestDto);
        return CarMapper.INSTANCE.toDto(carService.updateCar(id, car));
    }

    @Operation(summary = "Delete a car", description = "Delete an existing car (MANAGER only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden, only MANAGER can delete"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(
            @Parameter(description = "ID of the car to delete", required = true, example = "1")
            @PathVariable Long id) {
        carService.deleteCar(id);
    }
}
