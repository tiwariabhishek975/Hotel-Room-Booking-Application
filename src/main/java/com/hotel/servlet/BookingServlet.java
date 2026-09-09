package com.hotel.servlet;

import com.hotel.model.Booking;
import com.hotel.model.User;
import com.hotel.service.BookingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/user/book")
public class BookingServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");

            Booking booking = new Booking();
            booking.setUserId(user.getId());
            booking.setRoomId(Integer.parseInt(req.getParameter("roomId")));
            booking.setCheckInDate(LocalDate.parse(req.getParameter("checkIn")));
            booking.setCheckOutDate(LocalDate.parse(req.getParameter("checkOut")));

            bookingService.createBooking(booking);
            resp.sendRedirect(req.getContextPath() + "/user/bookings?success=true");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/rooms?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}
