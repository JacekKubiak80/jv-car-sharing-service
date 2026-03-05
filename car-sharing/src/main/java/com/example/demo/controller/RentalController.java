package com.example.demo.controller;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<RentalResponseDto> getAllRentals(@RequestParam(required = false) Long userId,
                                                 @RequestParam(required = false) Boolean isActive) {
        if (userId != null && isActive != null) {
            return rentalService.getRentalsByUserAndStatus(userId, isActive);
        } else {
            return rentalService.getAllRentals();
        }
    }

    @Operation(summary = "Get rental by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return rentalService.getRentalById(id, email);
    }

    @Operation(summary = "Create a new rental and decrease car inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rental created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid rental data")
    })
    @PostMapping
    public RentalResponseDto createRental(@RequestParam Long carId, @RequestParam Long userId) {
        return rentalService.createRental(carId, userId);
    }

    @Operation(summary = "Return a rental and increase car inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental returned successfully"),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id) {
        return rentalService.returnRental(id);
    }
}
