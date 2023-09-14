package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestParam Long rentalId,
                                 @RequestParam Payment.PaymentType type,
                                 @RequestParam String sessionUrl,
                                 @RequestParam String sessionId) {
        return paymentService.createPayment(rentalId, type, sessionUrl, sessionId);
    }

    @GetMapping("/{rentalId}")
    public Payment getPaymentByRental(@PathVariable Long rentalId) {
        return paymentService.getPaymentByRental(rentalId);
    }

    @PutMapping("/{paymentId}/status")
    public Payment updatePaymentStatus(@PathVariable Long paymentId,
                                       @RequestParam Payment.PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }
}
