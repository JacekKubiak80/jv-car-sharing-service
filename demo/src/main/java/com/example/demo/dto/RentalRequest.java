package com.example.demo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequest {

    @NotNull(message = "Car ID cannot be null")
    private Long carId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Return date cannot be null")
    @FutureOrPresent(message = "Return date must be in the future or today")
    private LocalDate returnDate;
}
