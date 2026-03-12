package com.example.demo.controller;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.service.PaymentService;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment management", description = "Endpoints for managing payments")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForUser(@RequestParam("user_id") Long userId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsForUser(userId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestParam Long rentalId, @RequestParam Long userId) {
        PaymentResponseDto payment = paymentService.createPaymentSession(rentalId, userId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/success/{sessionId}")
    public ResponseEntity<PaymentResponseDto> paymentSuccess(@PathVariable String sessionId) {
        PaymentResponseDto payment = paymentService.getPaymentSuccess(sessionId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/cancel/{sessionId}")
    public ResponseEntity<PaymentResponseDto> paymentCancel(@PathVariable String sessionId) {
        PaymentResponseDto payment = paymentService.getPaymentCancel(sessionId);
        return ResponseEntity.ok(payment);
    }
}
