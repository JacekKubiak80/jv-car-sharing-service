package com.carsharing.app.service;

import com.carsharing.app.dto.PaymentResponseDto;
import java.util.List;

public interface PaymentService {

    List<PaymentResponseDto> getAllPayments();

    List<PaymentResponseDto> getPaymentsForUser(Long userId);

    PaymentResponseDto createPaymentSession(Long rentalId, Long userId);

    PaymentResponseDto getPaymentSuccess(String sessionId);

    PaymentResponseDto getPaymentCancel(String sessionId);
}
