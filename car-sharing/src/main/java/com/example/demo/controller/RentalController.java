package com.example.demo.controller;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Rental Controller", description = "Operations on car rentals")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<RentalResponseDto> getAllRentals(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Boolean isActive) {
        return rentalService.getRentals(userId, isActive);
    }

    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id, java.security.Principal principal) {
        return rentalService.getRentalById(id, principal.getName());
    }

    @PostMapping
    public RentalResponseDto createRental(@RequestParam Long carId, @RequestParam Long userId) {
        return rentalService.createRental(carId, userId);
    }

    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id) {
        return rentalService.returnRental(id);
    }
}
