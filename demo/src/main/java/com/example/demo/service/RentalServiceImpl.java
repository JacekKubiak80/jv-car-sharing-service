package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.mapper.RentalMapper;
import com.example.demo.model.Rental;
import com.example.demo.repository.RentalRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    public List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalResponseDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<Rental> getRentalsForUser(String email) {
        return rentalRepository.findByUserEmail(email);
    }
}
