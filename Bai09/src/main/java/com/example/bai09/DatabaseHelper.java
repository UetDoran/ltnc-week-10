package com.example.bai09;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void saveUser(String name, boolean agreed) throws SQLException {
        String sql = "INSERT INTO users (fullname, status) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setBoolean(2, agreed);
            pstmt.executeUpdate();
        }
    }

    public static User getLastUser() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new User(rs.getString("fullname"), rs.getBoolean("status"));
            }
        }
        return null;
    }
}