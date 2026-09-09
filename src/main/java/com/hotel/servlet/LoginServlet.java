package com.hotel.servlet;

import com.hotel.model.User;
import com.hotel.service.AuthService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = authService.login(req.getParameter("email"), req.getParameter("password"));
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/user/login.jsp?error=Invalid+credentials");
                return;
            }

            HttpSession old = req.getSession(false);
            if (old != null) old.invalidate();

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);

            if ("ADMIN".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp");
            }
        } catch (Exception e) {
            // FIXED: Added leading slash and ".jsp" extension
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp?error=" +
                    java.net.URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Login error", java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}