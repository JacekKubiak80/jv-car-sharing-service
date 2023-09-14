package com.example.demo.service;

import com.example.demo.exception.RentalAlreadyReturnedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Rental;
import com.example.demo.model.User;
import com.example.demo.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final NotificationService notificationService;

    public Rental createRental(Rental rental) {
        carService.decreaseInventory(rental.getCar().getId());

        rental.setRentalDate(LocalDate.now());
        Rental savedRental = rentalRepository.save(rental);

        notificationService.sendRentalCreated(savedRental);
        return savedRental;
    }

    public Rental returnRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with ID " + rentalId));
        if (rental.getActualReturnDate() != null) {
            throw new RentalAlreadyReturnedException("Rental with ID " + rentalId + " has already been returned");
        }
        rental.setActualReturnDate(LocalDate.now());
        carService.increaseInventory(rental.getCar().getId());
        ;
        rentalRepository.save(rental);

        notificationService.sendRentalReturned(rental);
        return rental;
    }

    public List<Rental> getRentalsForUser(User user, Boolean isActive) {
        if (isActive == null) {
            return rentalRepository.findByUser(user);
        } else if (isActive) {
            return rentalRepository.findByUserAndActualReturnDateIsNull(user);
        } else {
            return rentalRepository.findByUserAndActualReturnDateIsNotNull(user);
        }
    }
}
