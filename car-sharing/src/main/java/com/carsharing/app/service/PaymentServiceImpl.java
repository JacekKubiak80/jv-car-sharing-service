package com.carsharing.app.service;

import com.carsharing.app.dto.PaymentResponseDto;
import com.carsharing.app.exception.AccessDeniedException;
import com.carsharing.app.exception.PaymentCreationException;
import com.carsharing.app.exception.ResourceNotFoundException;
import com.carsharing.app.mapper.PaymentMapper;
import com.carsharing.app.model.Payment;
import com.carsharing.app.model.Payment.PaymentStatus;
import com.carsharing.app.model.Rental;
import com.carsharing.app.repository.PaymentRepository;
import com.carsharing.app.repository.RentalRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.List;
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

        if (!rental.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(
                    "You cannot create payment for another user's rental");
        }

        BigDecimal totalPrice = rental.getTotalPrice();
        long amount = totalPrice.multiply(BigDecimal.valueOf(100)).longValue();

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/payments/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:8080/payments/cancel?session_id={CHECKOUT_SESSION_ID}")
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
            throw new PaymentCreationException("Stripe session creation failed", e);
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
}
