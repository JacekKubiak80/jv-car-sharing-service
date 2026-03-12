package com.example.demo.controller;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rental Controller", description = "Operations on car rentals")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary = "Get all rentals for the logged-in user, optionally filter by active status")
    @GetMapping
    public List<RentalResponseDto> getAllRentals(@RequestParam(required = false) Boolean isActive) {

        return rentalService.getRentals(null, isActive);
    }

    @Operation(summary = "Get details of a specific rental by its ID")
    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id) {

        return rentalService.getRentalById(id);
    }

    @Operation(summary = "Create a new rental for a car")
    @PostMapping
    public RentalResponseDto createRental(@RequestParam Long carId) {

        return rentalService.createRental(carId);
    }

    @Operation(summary = "Return a rented car by rental ID")
    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id) {

        return rentalService.returnRental(id);
    }
}
