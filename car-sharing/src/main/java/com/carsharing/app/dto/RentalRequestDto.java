package com.carsharing.app.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class RentalRequestDto {

    @NotNull(message = "Car ID cannot be null")
    private Long carId;

    @NotNull(message = "Return date cannot be null")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDate returnDate;
}
