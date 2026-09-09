package com.hotel.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@WebServlet("/user/upload")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class FileUploadServlet extends HttpServlet {
    private static final Set<String> ALLOWED = Set.of("jpg","jpeg","png","pdf");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Part file = req.getPart("file");
        if (file == null || file.getSize() == 0) {
            resp.sendError(400, "File is required");
            return;
        }

        String original = Paths.get(file.getSubmittedFileName()).getFileName().toString();
        String ext = original.contains(".") ? original.substring(original.lastIndexOf('.') + 1).toLowerCase() : "";
        if (!ALLOWED.contains(ext)) {
            resp.sendError(400, "Only JPG, JPEG, PNG and PDF are allowed");
            return;
        }

        Path uploadDir = Paths.get(System.getProperty("user.home"), "hotel-booking-uploads");
        Files.createDirectories(uploadDir);
        String savedName = UUID.randomUUID() + "." + ext;
        file.write(uploadDir.resolve(savedName).toString());

        resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp?upload=success");
    }
}
