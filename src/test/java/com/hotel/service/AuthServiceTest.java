package com.hotel.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private final AuthService service = new AuthService();

    @Test
    void validRegistrationDataShouldPass() {
        assertDoesNotThrow(() ->
                service.validateRegistration("Abhishek", "test@example.com", "secret123"));
    }

    @Test
    void invalidEmailShouldFail() {
        assertThrows(IllegalArgumentException.class, () ->
                service.validateRegistration("Abhishek", "wrong-email", "secret123"));
    }

    @Test
    void shortPasswordShouldFail() {
        assertThrows(IllegalArgumentException.class, () ->
                service.validateRegistration("Abhishek", "test@example.com", "123"));
    }
}
