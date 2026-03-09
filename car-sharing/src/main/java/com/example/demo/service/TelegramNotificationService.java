package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationService implements NotificationService {

    @Override
    public void sendRentalCreated(RentalResponseDto rentalDto) {
        System.out.println("Telegram: Rental created: " + rentalDto.getId());
    }

    @Override
    public void sendRentalReturned(RentalResponseDto rentalDto) {
        System.out.println("Telegram: Rental returned: " + rentalDto.getId());
    }

    @Override
    public void sendUserRegistered(User user) {
        System.out.println("User registered: " + user.getEmail());
    }

    @Override
    public void sendUserProfileUpdated(User user) {
        System.out.println("User profile updated: " + user.getEmail());
    }
}
