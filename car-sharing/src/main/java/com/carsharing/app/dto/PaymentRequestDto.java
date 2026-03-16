package com.carsharing.app.dto;

import com.carsharing.app.model.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull(message = "Rental ID cannot be null")
    private Long rentalId;

    @NotNull(message = "Payment type cannot be null")
    private Payment.PaymentType type;
}
