package com.example.demo.service;

import com.example.demo.dto.RentalResponseDto;
import com.example.demo.model.User;

public interface NotificationService {

    void sendRentalCreated(RentalResponseDto rentalDto);

    void sendRentalReturned(RentalResponseDto rentalDto);

    void sendUserRegistered(User user);

    void sendUserProfileUpdated(User user);
}
