package com.hotel.service;

import com.hotel.dao.BookingDAO;
import com.hotel.model.Booking;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public Map<String, Long> bookingStatusReport() throws SQLException {
        return bookingDAO.findAll().stream()
                .collect(Collectors.groupingBy(Booking::getStatus, Collectors.counting()));
    }

    public BigDecimal confirmedAmount() throws SQLException {
        return bookingDAO.findAll().stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()) || "CHECKED_IN".equals(b.getStatus()))
                .map(Booking::getTotalAmount)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Booking> confirmedBookings() throws SQLException {
        return bookingDAO.findAll().stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .toList();
    }
}
