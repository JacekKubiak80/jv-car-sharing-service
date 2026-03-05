package com.example.demo.mapper;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {CarMapper.class})
public abstract class RentalMapper {

    @Autowired
    protected CarMapper carMapper;

    public RentalResponseDto toDto(Rental rental) {
        if (rental == null) {
            return null;
        }
        return RentalResponseDto.builder()
                .id(rental.getId())
                .car(carMapper.toDto(rental.getCar()))
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .actualReturnDate(rental.getActualReturnDate())
                .active(rental.getActualReturnDate() == null)
                .build();
    }
}
