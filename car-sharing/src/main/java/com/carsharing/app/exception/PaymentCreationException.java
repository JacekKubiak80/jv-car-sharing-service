package com.carsharing.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentCreationException extends RuntimeException {

    public PaymentCreationException(String message) {
        super(message);
    }

    public PaymentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
