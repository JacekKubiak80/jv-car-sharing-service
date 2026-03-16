package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {



    RentalResponseDto getRentalById(Long id);

    List<RentalResponseDto> getRentals(Boolean isActive);

    RentalResponseDto createRental(Long carId, LocalDate returnDate);

    RentalResponseDto returnRental(Long rentalId);
}
