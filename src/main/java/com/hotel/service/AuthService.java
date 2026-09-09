package com.hotel.service;

import com.hotel.dao.UserDAO;
import com.hotel.model.User;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validateRegistration(String name, String email, String password) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (email == null || !EMAIL.matcher(email).matches()) throw new IllegalArgumentException("Invalid email");
        if (password == null || password.length() < 6)
            throw new IllegalArgumentException("Password must contain at least 6 characters");
    }

    public boolean register(User user) throws SQLException {
        validateRegistration(user.getName(), user.getEmail(), user.getPassword());
        if (userDAO.emailExists(user.getEmail())) throw new IllegalArgumentException("Email already registered");
        return userDAO.register(user);
    }

    public User login(String email, String password) throws SQLException {
        if (email == null || password == null || email.isBlank() || password.isBlank())
            throw new IllegalArgumentException("Email and password are required");
        return userDAO.login(email, password);
    }
}
