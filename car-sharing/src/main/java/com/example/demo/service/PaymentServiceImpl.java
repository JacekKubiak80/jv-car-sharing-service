package com.example.demo.service;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.model.Payment;
import com.example.demo.model.Payment.PaymentStatus;
import com.example.demo.model.Rental;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RentalRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDto> getPaymentsForUser(Long userId) {
        return paymentRepository.findByRentalUserId(userId)
                .stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDto createPaymentSession(Long rentalId, Long userId) {
        Stripe.apiKey = stripeSecretKey;

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: "
                        + rentalId));

        BigDecimal totalPrice = rental.getTotalPrice();
        long amount = totalPrice.multiply(BigDecimal.valueOf(100)).longValue();

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/payments/success/{CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:8080/payments/cancel/{CHECKOUT_SESSION_ID}")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(amount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData
                                                                    .ProductData.builder()
                                                                    .setName("Car rental payment")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            Payment payment = new Payment();
            payment.setSessionId(session.getId());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setRental(rental);
            payment.setAmountToPay(totalPrice);
            payment.setSessionUrl(session.getUrl());

            paymentRepository.save(payment);

            return paymentMapper.toDto(payment);

        } catch (Exception e) {
            throw new RuntimeException("Stripe session creation failed", e);
        }
    }

    @Override
    public PaymentResponseDto getPaymentSuccess(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with sessionId: " + sessionId));

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentCancel(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with sessionId: " + sessionId));

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentSuccess(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: "
                        + rentalId));

        Payment payment = paymentRepository.findByRental(rental)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found for this rental"));

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentCancel(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rental not found with id: " + rentalId));

        Payment payment = paymentRepository.findByRental(rental)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found for this rental"));

        return paymentMapper.toDto(payment);
    }
}
