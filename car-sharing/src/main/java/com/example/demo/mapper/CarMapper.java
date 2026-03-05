package com.example.demo.mapper;

import com.example.demo.dto.CarRequestDto;
import com.example.demo.dto.CarResponseDto;
import com.example.demo.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarResponseDto toDto(Car car);

    Car toEntity(CarRequestDto dto);

    void updateEntityFromDto(CarRequestDto dto, @MappingTarget Car entity);
}
