package com.hotel.config;



import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("Starting Hotel Booking application...");

        DatabaseInitializer.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        System.out.println("Hotel Booking application stopped.");
    }
}