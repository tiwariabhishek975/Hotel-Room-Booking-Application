package com.hotel.servlet;

import com.hotel.dao.BookingDAO;
import com.hotel.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/user/bookings")
public class UserBookingsServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        try {
            User user = (User) req.getSession().getAttribute("user");
            req.setAttribute("bookings", bookingDAO.findByUser(user.getId()));
            req.getRequestDispatcher("/bookings.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }
}
