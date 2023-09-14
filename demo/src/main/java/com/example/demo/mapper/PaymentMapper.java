package com.example.demo.mapper;


import com.example.demo.dto.PaymentResponse;
import com.example.demo.model.Payment;

public class PaymentMapper {

    private PaymentMapper() {}

    public static PaymentResponse toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentResponse.builder()
                .id(payment.getId())
                .status(Payment.PaymentStatus.valueOf(payment.getStatus().name()))
                .type(Payment.PaymentType.valueOf(payment.getType().name()))
                .rentalId(payment.getRental().getId())
                .sessionUrl(payment.getSessionUrl())
                .amountToPay(payment.getAmountToPay())
                .build();
    }
}
