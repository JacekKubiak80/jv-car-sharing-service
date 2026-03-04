package com.example.demo.controller;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rental Controller", description = "Operations for managing car rentals")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary = "Get all rentals (MANAGER only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All rentals retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-manager users")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<RentalResponseDto> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Operation(summary = "Get rental by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }
}
