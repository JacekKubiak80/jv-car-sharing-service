package com.example.demo.mapper;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.Rental;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CarMapper.class})
public interface RentalMapper {

    default RentalResponseDto toDto(Rental rental) {
        if (rental == null) return null;
        return RentalResponseDto.builder()
                .id(rental.getId())
                .car(CarMapper.INSTANCE.toDto(rental.getCar()))
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .actualReturnDate(rental.getActualReturnDate())
                .active(rental.getActualReturnDate() == null)
                .build();
    }
}
