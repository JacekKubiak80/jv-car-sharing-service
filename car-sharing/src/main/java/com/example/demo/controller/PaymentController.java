package com.example.demo.controller;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.model.Payment.PaymentType;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment management", description = "Endpoints for managing payments")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get all payments for a specific user (manager only)")
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForUser(@RequestParam("user_id") Long userId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsForUser(userId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Create a new payment session for a rental (logged-in user)")
    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestParam Long rentalId,
            @RequestParam PaymentType paymentType,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        PaymentResponseDto payment = paymentService.createPaymentSession(rentalId, userId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Handle successful payment by session ID")
    @GetMapping("/success")
    public ResponseEntity<PaymentResponseDto> paymentSuccess(@RequestParam("session_id") String sessionId) {
        PaymentResponseDto payment = paymentService.getPaymentSuccess(sessionId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Handle cancelled payment by session ID")
    @GetMapping("/cancel")
    public ResponseEntity<PaymentResponseDto> paymentCancel(@RequestParam("session_id") String sessionId) {
        PaymentResponseDto payment = paymentService.getPaymentCancel(sessionId);
        return ResponseEntity.ok(payment);
    }
}
