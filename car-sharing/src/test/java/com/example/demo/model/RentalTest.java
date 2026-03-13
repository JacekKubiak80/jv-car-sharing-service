package com.example.demo.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    @Test
    void getTotalPrice_ShouldCalculateCorrectPrice_WhenActualReturnDateSet() {
        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(100))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .actualReturnDate(LocalDate.now().plusDays(3))
                .build();

        BigDecimal price = rental.getTotalPrice();
        assertEquals(BigDecimal.valueOf(300), price);
    }

    @Test
    void getTotalPrice_ShouldCalculateCorrectPrice_WhenOnlyExpectedReturnDateSet() {
        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(80))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .expectedReturnDate(LocalDate.now().plusDays(2))
                .build();

        BigDecimal price = rental.getTotalPrice();
        assertEquals(BigDecimal.valueOf(160), price);
    }

    @Test
    void getTotalPrice_ShouldReturnOneDayPrice_WhenNoReturnDatesSet() {
        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(50))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .build();

        BigDecimal price = rental.getTotalPrice();
        assertEquals(BigDecimal.valueOf(50), price);
    }

    @Test
    void getTotalPrice_ShouldReturnOneDayPrice_WhenRentalAndReturnOnSameDay() {
        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(120))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .actualReturnDate(LocalDate.now())
                .build();

        BigDecimal price = rental.getTotalPrice();
        assertEquals(BigDecimal.valueOf(120), price);
    }
}