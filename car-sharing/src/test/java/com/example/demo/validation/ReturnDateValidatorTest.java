package com.example.demo.validation;


import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReturnDateValidatorTest {

    private final ReturnDateValidator validator = new ReturnDateValidator();

    @Test
    void isValid_ShouldReturnFalse_WhenDateIsNull() {
        boolean result = validator.isValid(null, null);
        assertFalse(result);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenDateIsToday() {
        boolean result = validator.isValid(LocalDate.now(), null);
        assertTrue(result);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenDateIsInPast() {
        boolean result = validator.isValid(LocalDate.now().minusDays(1), null);
        assertFalse(result);
    }
}