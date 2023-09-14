package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RentalAlreadyReturnedException extends RuntimeException {

    public RentalAlreadyReturnedException(String message) {
        super(message);
    }

    public RentalAlreadyReturnedException(String message, Throwable cause) {
        super(message, cause);
    }
}
