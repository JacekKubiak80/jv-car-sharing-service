package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import java.util.List;

public interface RentalService {

    List<RentalResponseDto> getAllRentals();

    RentalResponseDto getRentalById(Long id, String email);

    List<RentalResponseDto> getRentalsByUserAndStatus(Long userId, Boolean isActive);

    RentalResponseDto createRental(Long carId, Long userId);

    RentalResponseDto returnRental(Long rentalId);

    List<RentalResponseDto> getRentals(Long userId, Boolean isActive);
}
