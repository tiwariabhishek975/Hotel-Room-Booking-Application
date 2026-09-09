package com.hotel.servlet;

import com.hotel.dao.BookingDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/bookings")
public class AdminBookingServlet extends HttpServlet {
    private final BookingDAO dao = new BookingDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        try {
            req.setAttribute("bookings", dao.findAll());
            req.getRequestDispatcher("/admin-bookings.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            dao.updateStatus(Integer.parseInt(req.getParameter("id")), req.getParameter("status"));
            resp.sendRedirect(req.getContextPath() + "/admin/bookings.jsp");
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
