package com.example.demo.dto;

import com.example.demo.model.Payment;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private Long rentalId;
    private Payment.PaymentType type;
    private Payment.PaymentStatus status;
    private String sessionUrl;
    private String sessionId;
    private BigDecimal amountToPay;
}
