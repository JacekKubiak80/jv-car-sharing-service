package com.carsharing.app.mapper;

import com.carsharing.app.dto.RentalResponseDto;
import com.carsharing.app.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarMapper.class})
public interface RentalMapper {

    @Mapping(target = "active", expression = "java(rental.getActualReturnDate() == null)")

    @Mapping(target = "totalPrice", expression = "java(rental.getTotalPrice())")
    RentalResponseDto toDto(Rental rental);
}

