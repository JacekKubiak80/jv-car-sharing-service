package com.carsharing.app.repository;

import com.carsharing.app.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    List<Rental> findByUserIdAndActualReturnDateIsNull(Long userId);

    List<Rental> findByUserIdAndActualReturnDateIsNotNull(Long userId);

    List<Rental> findByUserEmail(String email);

    @Query("SELECT r FROM Rental r WHERE r.user.id = :userId AND r.actualReturnDate IS NULL")
    List<Rental> findActiveRentalsByUser(Long userId);

    @Query("SELECT r FROM Rental r WHERE r.user.id = :userId AND r.actualReturnDate IS NOT NULL")
    List<Rental> findCompletedRentalsByUser(Long userId);
}
