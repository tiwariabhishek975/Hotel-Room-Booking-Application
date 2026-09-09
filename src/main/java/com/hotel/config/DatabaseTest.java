package com.hotel.config;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        try {

            Connection connection = DBConnection.getConnection();

            System.out.println("=================================");
            System.out.println("MYSQL CONNECTION SUCCESSFUL");
            System.out.println("=================================");

            System.out.println("Database: "
                    + connection.getCatalog());

            connection.close();

        } catch (Exception e) {

            System.out.println("=================================");
            System.out.println("MYSQL CONNECTION FAILED");
            System.out.println("=================================");

            e.printStackTrace();
        }
    }
}