package com.hotel.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private final BookingService service = new BookingService();

    @Test
    void checkoutMustBeAfterCheckin() {
        assertThrows(IllegalArgumentException.class, () ->
                service.validateDates(
                        LocalDate.of(2026, 9, 10),
                        LocalDate.of(2026, 9, 10)));
    }

    @Test
    void validDatesShouldPass() {
        assertDoesNotThrow(() ->
                service.validateDates(
                        LocalDate.of(2026, 9, 10),
                        LocalDate.of(2026, 9, 12)));
    }
}
