package com.example.demo.service;

import com.example.demo.dto.PaymentResponseDto;
import java.util.List;

public interface PaymentService {

    List<PaymentResponseDto> getAllPayments();

    List<PaymentResponseDto> getPaymentsForUser(Long userId);

    PaymentResponseDto createPaymentSession(Long rentalId, Long userId);

    PaymentResponseDto getPaymentSuccess(String sessionId);

    PaymentResponseDto getPaymentCancel(String sessionId);

    PaymentResponseDto getPaymentSuccess(Long rentalId);

    PaymentResponseDto getPaymentCancel(Long rentalId);
}
