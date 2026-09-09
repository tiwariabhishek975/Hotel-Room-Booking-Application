package com.hotel.config;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {

        String[] sqlStatements = {

                // Users table
                """
            CREATE TABLE IF NOT EXISTS users (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(150) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                role ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """,

                // Rooms table
                """
            CREATE TABLE IF NOT EXISTS rooms (
                id INT PRIMARY KEY AUTO_INCREMENT,
                room_number VARCHAR(20) NOT NULL UNIQUE,
                room_type VARCHAR(30) NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                capacity INT NOT NULL,
                status ENUM('AVAILABLE','MAINTENANCE','INACTIVE') DEFAULT 'AVAILABLE',
                image_path VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """,

                // Bookings table
                """
            CREATE TABLE IF NOT EXISTS bookings (
                id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT NOT NULL,
                room_id INT NOT NULL,
                check_in_date DATE NOT NULL,
                check_out_date DATE NOT NULL,
                status ENUM(
                    'CONFIRMED',
                    'CHECKED_IN',
                    'CHECKED_OUT',
                    'CANCELLED'
                ) DEFAULT 'CONFIRMED',
                total_amount DECIMAL(10,2),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                CONSTRAINT fk_booking_user
                    FOREIGN KEY(user_id) REFERENCES users(id),

                CONSTRAINT fk_booking_room
                    FOREIGN KEY(room_id) REFERENCES rooms(id)
            )
            """,

                // Documents table
                """
            CREATE TABLE IF NOT EXISTS documents (
                id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT NOT NULL,
                booking_id INT NULL,
                file_name VARCHAR(255) NOT NULL,
                file_path VARCHAR(500) NOT NULL,
                file_type VARCHAR(50),
                uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                CONSTRAINT fk_document_user
                    FOREIGN KEY(user_id) REFERENCES users(id),

                CONSTRAINT fk_document_booking
                    FOREIGN KEY(booking_id) REFERENCES bookings(id)
            )
            """,

                // Check-in / Check-out table
                """
            CREATE TABLE IF NOT EXISTS checkin_checkout (
                id INT PRIMARY KEY AUTO_INCREMENT,
                booking_id INT NOT NULL,
                check_in_time DATETIME NULL,
                check_out_time DATETIME NULL,
                status VARCHAR(30),

                CONSTRAINT fk_ci_booking
                    FOREIGN KEY(booking_id) REFERENCES bookings(id)
            )
            """
        };

        try (Connection connection = DBConnection.getConnection()) {

            for (String sql : sqlStatements) {

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            }

            insertDefaultData(connection);

            System.out.println("======================================");
            System.out.println("Database initialization completed.");
            System.out.println("======================================");

        } catch (SQLException e) {

            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }

    private static void insertDefaultData(Connection connection)
            throws SQLException {

        String adminSql = """
            INSERT INTO users(name, email, password, role)
            SELECT 'System Admin',
                   'admin@hotel.com',
                   'admin123',
                   'ADMIN'
            WHERE NOT EXISTS (
                SELECT 1
                FROM users
                WHERE email = 'admin@hotel.com'
            )
            """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(adminSql);
        }

        String roomsSql = """
            INSERT INTO rooms
                (room_number, room_type, price, capacity, status)
            SELECT '101', 'STANDARD', 1800, 2, 'AVAILABLE'
            WHERE NOT EXISTS (
                SELECT 1 FROM rooms WHERE room_number = '101'
            )
            """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(roomsSql);
        }

        insertRoom(connection, "102", "STANDARD", 1800, 2);
        insertRoom(connection, "201", "DELUXE", 2800, 3);
        insertRoom(connection, "202", "DELUXE", 2800, 3);
        insertRoom(connection, "301", "SUITE", 4500, 4);
        insertRoom(connection, "302", "SUITE", 5000, 4);
        insertRoom(connection, "401", "DELUXE", 3200, 3);
        insertRoom(connection, "402", "STANDARD", 1600, 2);
    }

    private static void insertRoom(
            Connection connection,
            String roomNumber,
            String roomType,
            double price,
            int capacity) throws SQLException {

        String sql = """
            INSERT INTO rooms
                (room_number, room_type, price, capacity, status)
            SELECT '%s', '%s', %s, %d, 'AVAILABLE'
            WHERE NOT EXISTS (
                SELECT 1
                FROM rooms
                WHERE room_number = '%s'
            )
            """.formatted(
                roomNumber,
                roomType,
                price,
                capacity,
                roomNumber
        );

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
}