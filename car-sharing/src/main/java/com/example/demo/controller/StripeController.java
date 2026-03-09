package com.example.demo.controller;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.service.StripeService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/pay")
    public PaymentResponseDto pay(@RequestParam BigDecimal amount) {
        return stripeService.createPayment(amount);
    }
}
