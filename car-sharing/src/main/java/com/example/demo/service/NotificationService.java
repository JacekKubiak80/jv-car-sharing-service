package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendRentalCreated(RentalResponseDto rentalDto) {
        System.out.println("Rental created: " + rentalDto.getId());
    }

    public void sendRentalReturned(RentalResponseDto rentalDto) {
        System.out.println("Rental returned: " + rentalDto.getId());
    }
}
