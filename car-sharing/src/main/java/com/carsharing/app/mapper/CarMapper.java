package com.carsharing.app.mapper;

import com.carsharing.app.dto.CarRequestDto;
import com.carsharing.app.dto.CarResponseDto;
import com.carsharing.app.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarResponseDto toDto(Car car);

    Car toEntity(CarRequestDto dto);

    void updateEntityFromDto(CarRequestDto dto, @MappingTarget Car entity);
}
