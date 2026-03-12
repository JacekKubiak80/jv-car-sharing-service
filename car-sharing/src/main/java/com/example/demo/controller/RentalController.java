package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.RentalResponseDto;
import com.example.demo.service.RentalService;

@Tag(name = "Rental Controller", description = "Operations on car rentals")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;


    @GetMapping
    public List<RentalResponseDto> getAllRentals(@RequestParam(required = false) Boolean isActive,
                                                 Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return rentalService.getRentals(userId, isActive);
    }

    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id, Principal principal) {
        return rentalService.getRentalById(id, principal.getName());
    }

    @PostMapping
    public RentalResponseDto createRental(@RequestParam Long carId, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return rentalService.createRental(carId, userId);
    }

    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id, Principal principal) {
        return rentalService.returnRental(id);
    }

    private boolean userIsManager(Principal principal) {
        return false;
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        return 0L;
    }
}
