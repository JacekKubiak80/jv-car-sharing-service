package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {

    List<RentalResponseDto> getAllRentals();

    RentalResponseDto getRentalById(Long id);

    List<RentalResponseDto> getRentals(Long userId, Boolean isActive);

    RentalResponseDto createRental(Long carId, LocalDate returnDate);

    RentalResponseDto returnRental(Long rentalId);
}
