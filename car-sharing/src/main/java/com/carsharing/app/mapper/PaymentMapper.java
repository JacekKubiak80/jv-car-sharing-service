package com.carsharing.app.mapper;

import com.carsharing.app.dto.PaymentResponseDto;
import com.carsharing.app.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponseDto toDto(Payment payment);
}
