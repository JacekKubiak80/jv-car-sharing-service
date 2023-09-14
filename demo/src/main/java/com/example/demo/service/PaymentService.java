package com.example.demo.service;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Payment;
import com.example.demo.model.Rental;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;

    public Payment createPayment(Long rentalId, Payment.PaymentType type, String sessionUrl, String sessionId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with ID " + rentalId));

        BigDecimal amount = rental.getTotalPrice();

        Payment payment = Payment.builder()
                .rental(rental)
                .amountToPay(amount)
                .status(Payment.PaymentStatus.PENDING)
                .type(type)
                .sessionUrl(sessionUrl)
                .sessionId(sessionId)
                .build();

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with ID " + rentalId));

        return paymentRepository.findByRental(rental)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for rental ID " + rentalId));
    }

    public Payment updatePaymentStatus(Long paymentId, Payment.PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID " + paymentId));

        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}
