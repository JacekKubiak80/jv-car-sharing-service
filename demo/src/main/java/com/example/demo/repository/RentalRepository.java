package com.example.demo.repository;


import com.example.demo.model.Rental;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);

    List<Rental> findByUser(User user);

    List<Rental> findByUserAndActualReturnDateIsNull(User user);

    List<Rental> findByUserAndActualReturnDateIsNotNull(User user);
}
