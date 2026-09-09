package com.hotel.servlet;

import com.hotel.model.User;
import com.hotel.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            authService.register(user);
            resp.sendRedirect(req.getContextPath() + "/login.jsp?registered=true");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/register.jsp?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}
