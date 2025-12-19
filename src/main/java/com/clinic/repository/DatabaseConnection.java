package com.clinic.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;

    private DatabaseConnection() {
        try {
            // Замени на свои данные: имя_бд, логин, пароль
            String url = "jdbc:postgresql://localhost:5432/clinic_db";
            this.connection = DriverManager.getConnection(url, "postgres", "password");
        } catch (SQLException e) {
            throw new RuntimeException("Connection error", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}