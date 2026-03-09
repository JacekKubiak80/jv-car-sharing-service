package com.example.demo.service;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.model.Payment.PaymentStatus;
import com.example.demo.model.Payment.PaymentType;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public PaymentResponseDto createPayment(BigDecimal amount) {
        String sessionId = UUID.randomUUID().toString();
        String sessionUrl = "https://mock.stripe.com/session/" + sessionId;

        return PaymentResponseDto.builder()
                .id(1L)
                .type(PaymentType.PAYMENT)
                .status(PaymentStatus.PENDING)
                .amountToPay(amount)
                .sessionId(sessionId)
                .sessionUrl(sessionUrl)
                .build();
    }
}
