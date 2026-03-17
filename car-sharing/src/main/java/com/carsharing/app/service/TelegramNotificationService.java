package com.carsharing.app.service;

import com.carsharing.app.dto.CarResponseDto;
import com.carsharing.app.dto.RentalResponseDto;
import com.carsharing.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    private String buildUrl() {
        return "https://api.telegram.org/bot" + botToken + "/sendMessage";
    }

    private void sendMessage(String message) {
        try {
            restTemplate.postForObject(buildUrl(),
                    new TelegramMessage(chatId, message),
                    String.class);
        } catch (Exception e) {
            System.err.println("Failed to send Telegram message: " + e.getMessage());
        }
    }

    @Override
    public void sendRentalCreated(RentalResponseDto rentalDto) {
        CarResponseDto car = rentalDto.getCar();
        String carInfo = car.getBrand() + " " + car.getModel();
        String msg = String.format("Rental created!\nRental ID: %d\nCar: %s\nReturn date: %s",
                rentalDto.getId(),
                carInfo,
                rentalDto.getExpectedReturnDate());
        sendMessage(msg);
    }

    @Override
    public void sendRentalReturned(RentalResponseDto rentalDto) {
        CarResponseDto car = rentalDto.getCar();
        String carInfo = car.getBrand() + " " + car.getModel();
        String msg = String.format("✅ Rental returned!\nRental ID: %d\nCar: %s\nActual return: %s",
                rentalDto.getId(),
                carInfo,
                rentalDto.getActualReturnDate());
        sendMessage(msg);
    }

    @Override
    public void sendUserRegistered(User user) {
        String msg = String.format("🎉 New user registered!\nEmail: %s\nRole: %s",
                user.getEmail(),
                user.getRole());
        sendMessage(msg);
    }

    @Override
    public void sendUserProfileUpdated(User user) {
        String msg = String.format("✏️ User profile updated!\nEmail: %s\nRole: %s",
                user.getEmail(),
                user.getRole());
        sendMessage(msg);
    }

    private record TelegramMessage(String chat_id, String text) {}
}
