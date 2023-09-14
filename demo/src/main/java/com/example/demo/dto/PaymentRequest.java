package com.example.demo.dto;

import com.example.demo.model.Payment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Rental ID cannot be null")
    private Long rentalId;

    @NotNull(message = "Payment type cannot be null")
    private Payment.PaymentType type;

    @NotBlank(message = "Session URL cannot be blank")
    private String sessionUrl;

    @NotBlank(message = "Session ID cannot be blank")
    private String sessionId;
}
