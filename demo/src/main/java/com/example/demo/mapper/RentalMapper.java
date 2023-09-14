package com.example.demo.mapper;


import com.example.demo.dto.RentalResponse;
import com.example.demo.model.Rental;

public class RentalMapper {

    private RentalMapper() {}

    public static RentalResponse toDto(Rental rental) {
        if (rental == null) {
            return null;
        }

        return RentalResponse.builder()
                .id(rental.getId())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .actualReturnDate(rental.getActualReturnDate())
                .carId(rental.getCar().getId())
                .userId(rental.getUser().getId())
                .build();
    }
}
