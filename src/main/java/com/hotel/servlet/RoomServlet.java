package com.hotel.servlet;

import com.hotel.dao.RoomDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {
    private final RoomDAO roomDAO = new RoomDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        try {
            String keyword = req.getParameter("keyword");
            String type = req.getParameter("type");
            int page = 1;
            try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
            int pageSize = 6;

            req.setAttribute("rooms", roomDAO.search(keyword, type, page, pageSize));
            req.setAttribute("page", page);
            req.setAttribute("totalPages",
                    (int) Math.ceil((double) roomDAO.countAvailable(keyword, type) / pageSize));
            req.getRequestDispatcher("/rooms.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }
}
