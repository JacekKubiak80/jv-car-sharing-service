package com.example.demo.controller;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/user/{userId}")
    public List<PaymentResponseDto> getPaymentsForUser(@PathVariable Long userId) {
        return paymentService.getPaymentsForUser(userId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/all")
    public List<PaymentResponseDto> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
