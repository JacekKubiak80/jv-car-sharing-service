package com.example.demo.mapper;


import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponseDto toDto(Payment payment);
}
