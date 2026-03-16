package com.carsharing.app.service;

import com.carsharing.app.dto.RentalResponseDto;
import com.carsharing.app.model.User;

public interface NotificationService {

    void sendRentalCreated(RentalResponseDto rentalDto);

    void sendRentalReturned(RentalResponseDto rentalDto);

    void sendUserRegistered(User user);

    void sendUserProfileUpdated(User user);
}
