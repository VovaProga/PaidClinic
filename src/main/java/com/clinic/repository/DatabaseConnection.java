package com.clinic.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;

    private DatabaseConnection() {
        try {
            // Попробуй изменить порт на 5432, если 5433 не работает
            String url = "jdbc:postgresql://localhost:5432/clinic_db";
            this.connection = DriverManager.getConnection(url, "postgres", "1234");
            System.out.println("Успешное подключение к базе данных!");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
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