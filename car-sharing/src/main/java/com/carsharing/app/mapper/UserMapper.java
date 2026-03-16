package com.carsharing.app.mapper;

import com.carsharing.app.dto.UserRequestDto;
import com.carsharing.app.dto.UserResponseDto;
import com.carsharing.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto dto);

    void updateEntityFromDto(UserRequestDto dto, @MappingTarget User entity);
}
