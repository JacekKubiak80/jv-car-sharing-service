package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.CarOutOfStockException;
import com.example.demo.exception.RentalAlreadyReturnedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RentalMapper;
import com.example.demo.model.Car;
import com.example.demo.model.Rental;
import com.example.demo.model.User;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
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
    public List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalResponseDto getRentalById(Long id, String email) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        if (!rental.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You do not have permission to access this rental.");
        }
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<RentalResponseDto> getRentalsByUserAndStatus(Long userId, Boolean isActive) {
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

    @Override
    public RentalResponseDto createRental(Long carId, Long userId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (car.getInventory() <= 0) {
            throw new CarOutOfStockException("Car is not available for rental.");
        }

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalDate(java.time.LocalDate.now());
        rental.setActualReturnDate(null);
        rental.setExpectedReturnDate(java.time.LocalDate.now().plusDays(7));


        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);

        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(savedRental);
    }

    @Override
    public RentalResponseDto returnRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

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

    @Override
    public List<RentalResponseDto> getRentals(Long userId, Boolean isActive) {
        if (userId != null && isActive != null) {
            return getRentalsByUserAndStatus(userId, isActive);
        } else {
            return getAllRentals();
        }
    }
}
