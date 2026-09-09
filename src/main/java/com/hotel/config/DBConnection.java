package com.hotel.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
            getEnv("DB_URL",
                    "jdbc:mysql://localhost:3306/hotel_booking?useSSL=false&serverTimezone=UTC");

    private static final String USER =
            getEnv("DB_USERNAME", "root");

    private static final String PASSWORD =
            getEnv("DB_PASSWORD", "YOUR_PASSWORD");

    private DBConnection() {
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver NOT FOUND.",
                    e
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}