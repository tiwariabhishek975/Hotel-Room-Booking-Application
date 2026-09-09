# Hotel Room Booking System

Java Servlet/JSP + JDBC + MySQL project designed for a student assessment.

## Features
- Login & registration
- Admin/User role-based access
- User/Admin dashboards
- Room CRUD DAO methods
- Search, filter and pagination
- Image/PDF upload
- JavaMail notification service
- Stream API reports
- JUnit tests
- Session management
- Exception handling
- Input validation
- MySQL with JDBC
- Booking availability check
- Booking status lifecycle

## Important
This starter project intentionally keeps passwords in plain text to make the JDBC/authentication flow easy to understand for an assessment. For a production application, use BCrypt/Argon2 and environment variables/secrets.

## IntelliJ setup
1. Install JDK 17, IntelliJ IDEA, MySQL 8+, Maven and Tomcat 10.
2. Open this folder in IntelliJ as a Maven project.
3. Run `database/schema.sql` in MySQL Workbench.
4. Edit `DBConnection.java` and set your MySQL password.
5. Edit `EmailService.java` and configure a Gmail address + App Password if email testing is needed.
6. Add Tomcat 10 as a local application server in IntelliJ.
7. Create a Tomcat run configuration and deploy the WAR artifact `hotel-room-booking-system:war exploded`.
8. Run and open `/hotel-room-booking-system/`.

## Demo accounts
Admin:
email: admin@hotel.com
password: admin123

For safety, change this before any real deployment.

## Suggested next implementation steps
- Build Admin Room CRUD JSP + servlets.
- Add real document records to `documents`.
- Add check-in/check-out servlets that update `checkin_checkout`.
- Add email invocation after successful booking.
- Add password hashing.
- Add CSRF protection and stronger authorization.
