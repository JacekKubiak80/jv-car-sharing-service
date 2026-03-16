package com.carsharing.app.service;

import com.carsharing.app.dto.RentalResponseDto;
import com.carsharing.app.exception.AccessDeniedException;
import com.carsharing.app.exception.CarOutOfStockException;
import com.carsharing.app.exception.RentalAlreadyReturnedException;
import com.carsharing.app.exception.ResourceNotFoundException;
import com.carsharing.app.mapper.RentalMapper;
import com.carsharing.app.model.Car;
import com.carsharing.app.model.Rental;
import com.carsharing.app.model.User;
import com.carsharing.app.repository.CarRepository;
import com.carsharing.app.repository.RentalRepository;
import com.carsharing.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;



    @Override
    public RentalResponseDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        User currentUser = getCurrentUser();
        if (!rental.getUser().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != User.Role.MANAGER) {
            throw new AccessDeniedException("You do not have permission to access this rental.");
        }
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<RentalResponseDto> getRentals(Boolean isActive) {
        User currentUser = getCurrentUser();

        if (currentUser.getRole() == User.Role.MANAGER) {
            return rentalRepository.findAll()
                    .stream()
                    .map(rentalMapper::toDto)
                    .toList();
        }
        return getRentalsByUserAndStatus(currentUser.getId(), isActive);
    }

    @Override
    public RentalResponseDto createRental(Long carId, LocalDate returnDate) {
        User currentUser = getCurrentUser();
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        if (car.getInventory() <= 0) {
            throw new CarOutOfStockException("Car is not available for rental.");
        }

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(currentUser);
        rental.setRentalDate(java.time.LocalDate.now());
        rental.setActualReturnDate(null);
        rental.setExpectedReturnDate(returnDate);

        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);

        Rental savedRental = rentalRepository.save(rental);
        notificationService.sendRentalCreated(rentalMapper.toDto(savedRental));
        return rentalMapper.toDto(savedRental);
    }

    @Override
    public RentalResponseDto returnRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        User currentUser = getCurrentUser();
        if (!rental.getUser().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != User.Role.MANAGER) {
            throw new AccessDeniedException("You cannot return rentals of other users.");
        }
        if (rental.getActualReturnDate() != null) {
            throw new RentalAlreadyReturnedException("Rental has already been returned.");
        }
        rental.setActualReturnDate(java.time.LocalDate.now());
        rental.getCar().setInventory(rental.getCar().getInventory() + 1);
        carRepository.save(rental.getCar());
        Rental savedRental = rentalRepository.save(rental);
        notificationService.sendRentalReturned(rentalMapper.toDto(savedRental));
        return rentalMapper.toDto(savedRental);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else if (principal instanceof String) {
            email = (String) principal;
        } else {
            throw new AccessDeniedException("Cannot identify logged-in user");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private List<RentalResponseDto> getRentalsByUserAndStatus(Long userId, Boolean isActive) {
        List<Rental> rentals;
        if (isActive == null) {
            rentals = rentalRepository.findByUserId(userId);
        } else if (isActive) {
            rentals = rentalRepository.findByUserIdAndActualReturnDateIsNull(userId);
        } else {
            rentals = rentalRepository.findByUserIdAndActualReturnDateIsNotNull(userId);
        }
        return rentals.stream().map(rentalMapper::toDto).collect(Collectors.toList());
    }
}
