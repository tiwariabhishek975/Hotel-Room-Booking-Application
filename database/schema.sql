CREATE DATABASE IF NOT EXISTS hotel_booking;
USE hotel_booking;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(20) NOT NULL UNIQUE,
    room_type VARCHAR(30) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    capacity INT NOT NULL,
    status ENUM('AVAILABLE','MAINTENANCE','INACTIVE') DEFAULT 'AVAILABLE',
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status ENUM('CONFIRMED','CHECKED_IN','CHECKED_OUT','CANCELLED') DEFAULT 'CONFIRMED',
    total_amount DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_booking_room FOREIGN KEY(room_id) REFERENCES rooms(id)
);

CREATE TABLE documents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    booking_id INT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_type VARCHAR(50),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_document_booking FOREIGN KEY(booking_id) REFERENCES bookings(id)
);

CREATE TABLE checkin_checkout (
    id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    check_in_time DATETIME NULL,
    check_out_time DATETIME NULL,
    status VARCHAR(30),
    CONSTRAINT fk_ci_booking FOREIGN KEY(booking_id) REFERENCES bookings(id)
);

INSERT INTO users(name,email,password,role)
VALUES ('System Admin','admin@hotel.com','admin123','ADMIN');

INSERT INTO rooms(room_number,room_type,price,capacity,status) VALUES
('101','STANDARD',1800,2,'AVAILABLE'),
('102','STANDARD',1800,2,'AVAILABLE'),
('201','DELUXE',2800,3,'AVAILABLE'),
('202','DELUXE',2800,3,'AVAILABLE'),
('301','SUITE',4500,4,'AVAILABLE'),
('302','SUITE',5000,4,'AVAILABLE'),
('401','DELUXE',3200,3,'AVAILABLE'),
('402','STANDARD',1600,2,'AVAILABLE');
