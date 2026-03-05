package com.example.demo.repository;

import com.example.demo.model.Payment;
import com.example.demo.model.Rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRentalUserId(Long userId);

    Optional<Payment> findByRental(Rental rental);
}
