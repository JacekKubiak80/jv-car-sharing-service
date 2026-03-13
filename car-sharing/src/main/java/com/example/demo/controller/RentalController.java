package com.example.demo.controller;

import com.example.demo.dto.RentalRequestDto;
import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Rental Controller", description = "Operations on car rentals")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary =
            "Get all rentals for the logged-in user, optionally filter by active status")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<RentalResponseDto> getAllRentals(
            @RequestParam(required = false) Boolean isActive) {
        return rentalService.getRentals(isActive);
    }

    @Operation(summary = "Get details of a specific rental by its ID")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @Operation(summary = "Create a new rental for a car")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public RentalResponseDto createRental(@RequestBody @Valid RentalRequestDto rentalRequestDto) {
        return rentalService.createRental(
                rentalRequestDto.getCarId(),
                rentalRequestDto.getReturnDate()
        );
    }

    @Operation(summary = "Return a rented car by rental ID")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id) {
        return rentalService.returnRental(id);
    }
}
