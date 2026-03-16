package com.carsharing.app.dto;

import com.carsharing.app.model.Payment;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long id;
    private Payment.PaymentType type;
    private Payment.PaymentStatus status;
    private String sessionUrl;
    private BigDecimal amountToPay;
    private String sessionId;
}
