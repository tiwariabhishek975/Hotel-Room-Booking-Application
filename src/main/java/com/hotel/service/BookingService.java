package com.hotel.service;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Room;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    public void validateDates(java.time.LocalDate checkIn, java.time.LocalDate checkOut) {
        if (checkIn == null || checkOut == null) throw new IllegalArgumentException("Dates are required");
        if (!checkOut.isAfter(checkIn)) throw new IllegalArgumentException("Check-out must be after check-in");
    }

    public boolean createBooking(Booking booking) throws SQLException {
        validateDates(booking.getCheckInDate(), booking.getCheckOutDate());

        Room room = roomDAO.findById(booking.getRoomId());
        if (room == null) throw new IllegalArgumentException("Room not found");

        boolean available = bookingDAO.isRoomAvailable(
                booking.getRoomId(),
                Date.valueOf(booking.getCheckInDate()),
                Date.valueOf(booking.getCheckOutDate())
        );
        if (!available) throw new IllegalStateException("Room is not available for selected dates");

        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setTotalAmount(room.getPrice().multiply(BigDecimal.valueOf(nights)));
        return bookingDAO.create(booking);
    }
}
