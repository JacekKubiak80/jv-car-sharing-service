package com.example.demo.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    @Test
    void getTotalPrice_ShouldCalculateCorrectPrice_WhenMultipleDays() {

        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(100))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(3))
                .build();

        BigDecimal price = rental.getTotalPrice();

        assertEquals(BigDecimal.valueOf(300), price);
    }

    @Test
    void getTotalPrice_ShouldReturnOneDayPrice_WhenReturnSameDay() {

        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(50))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .build();

        BigDecimal price = rental.getTotalPrice();

        assertEquals(BigDecimal.valueOf(50), price);
    }

    @Test
    void getTotalPrice_ShouldReturnOneDayPrice_WhenReturnDateIsNull() {
        Car car = Car.builder()
                .dailyFee(BigDecimal.valueOf(100))
                .build();

        Rental rental = Rental.builder()
                .car(car)
                .rentalDate(LocalDate.now())
                .returnDate(null)
                .build();

        BigDecimal price = rental.getTotalPrice();
        assertEquals(BigDecimal.valueOf(100), price);
    }
}