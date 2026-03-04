package com.example.demo.controller;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment Controller", description = "Operations for managing payments and Stripe sessions")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get all payments for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public List<PaymentResponseDto> getPaymentsForUser(@PathVariable Long userId) {
        return paymentService.getPaymentsForUser(userId);
    }

    @Operation(summary = "Manager: Get all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All payments retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/all")
    public List<PaymentResponseDto> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
