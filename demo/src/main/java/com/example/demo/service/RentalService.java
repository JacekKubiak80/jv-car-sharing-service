package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.Rental;
import java.util.List;

public interface RentalService {

    List<RentalResponseDto> getAllRentals();

    RentalResponseDto getRentalById(Long id);

    List<Rental> getRentalsForUser(String email);
}
