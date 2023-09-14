package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarOutOfStockException extends RuntimeException {

    public CarOutOfStockException(String message) {
        super(message);
    }

    public CarOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
