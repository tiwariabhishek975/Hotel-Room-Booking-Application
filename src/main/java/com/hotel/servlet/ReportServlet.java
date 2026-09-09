package com.hotel.servlet;

import com.hotel.service.ReportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/reports")
public class ReportServlet extends HttpServlet {
    private final ReportService reportService = new ReportService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        try {
            req.setAttribute("statusReport", reportService.bookingStatusReport());
            req.setAttribute("confirmedAmount", reportService.confirmedAmount());
            req.getRequestDispatcher("/reports.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }
}
