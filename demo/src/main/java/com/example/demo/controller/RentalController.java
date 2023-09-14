package com.example.demo.controller;

import com.example.demo.model.Rental;
import com.example.demo.model.User;
import com.example.demo.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }

    @PostMapping("/{id}/return")
    public Rental returnRental(@PathVariable Long id) {
        return rentalService.returnRental(id);
    }

    @GetMapping
    public List<Rental> getRentalsForUser(@RequestBody User user,
                                          @RequestParam(required = false) Boolean isActive) {
        return rentalService.getRentalsForUser(user, isActive);
    }
}
