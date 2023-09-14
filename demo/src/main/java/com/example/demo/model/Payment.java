package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @Column(nullable = false)
    private String sessionUrl;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountToPay;

    public enum PaymentType {
        PAYMENT,
        FINE
    }

    public enum PaymentStatus {
        PENDING,
        PAID
    }
}
