package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor {
    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:minInventory IS NULL OR c.inventory >= :minInventory) AND " +
            "(:maxDailyFee IS NULL OR c.dailyFee <= :maxDailyFee)")
    Page<Car> findCarsWithFilters(String brand, Car.CarType type, Integer minInventory,
                                  BigDecimal maxDailyFee, Pageable pageable);
}
