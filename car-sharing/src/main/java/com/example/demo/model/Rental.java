package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentalDate;

    private LocalDate actualReturnDate;

    private LocalDate expectedReturnDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getTotalPrice() {
        LocalDate endDate = actualReturnDate != null ? actualReturnDate : expectedReturnDate;
        if (endDate == null) {
            endDate = rentalDate.plusDays(1);
        }
        long days = ChronoUnit.DAYS.between(rentalDate, endDate);
        if (days <= 0) {
            days = 1;
        }
        return car.getDailyFee().multiply(BigDecimal.valueOf(days));
    }
}
