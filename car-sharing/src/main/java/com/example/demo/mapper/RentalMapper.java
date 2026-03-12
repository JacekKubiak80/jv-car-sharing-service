package com.example.demo.mapper;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarMapper.class})
public interface RentalMapper {

    @Mapping(target = "active", expression = "java(rental.getActualReturnDate() == null)")

    @Mapping(target = "totalPrice", expression = "java(rental.getTotalPrice())")
    RentalResponseDto toDto(Rental rental);
}

