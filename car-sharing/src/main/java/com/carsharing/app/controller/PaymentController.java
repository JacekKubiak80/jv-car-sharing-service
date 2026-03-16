package com.carsharing.app.controller;

import com.carsharing.app.dto.PaymentResponseDto;
import com.carsharing.app.model.User;
import com.carsharing.app.service.PaymentService;
import com.carsharing.app.service.UserService;
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
    private final UserService userService;

    @Operation(summary = "Get all payments (manager only)")
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "Get all payments for a specific user (manager only)")
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForUser(
            @RequestParam("user_id") Long userId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsForUser(userId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Create a new payment session for a rental (logged-in user)")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestParam Long rentalId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.getUserEntityByEmail(email);
        Long userId = user.getId();

        PaymentResponseDto payment = paymentService.createPaymentSession(rentalId, userId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Handle successful payment by session ID")
    @GetMapping("/success")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> paymentSuccess(@RequestParam("session_id") String sessionId) {
        paymentService.getPaymentSuccess(sessionId);
        String frontendUrl = "https://your-frontend.com/payment-success";
        return ResponseEntity.status(303)
                .header("Location", frontendUrl)
                .build();
    }

    @Operation(summary = "Handle cancelled payment by session ID")
    @GetMapping("/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> paymentCancel(@RequestParam("session_id") String sessionId) {
        paymentService.getPaymentCancel(sessionId);
        String frontendUrl = "https://your-frontend.com/payment-cancel";
        return ResponseEntity.status(303)
                .header("Location", frontendUrl)
                .build();
    }
}
