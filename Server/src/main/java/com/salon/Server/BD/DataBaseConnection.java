package com.salon.Server.BD;


import com.salon.Server.Utils.PasswordUtil;

import java.sql.*;

public class DataBaseConnection {
    private static final String PORT = "3306";
    private static final String DB_NAME = "sales_management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DataBaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:" + PORT + "/" + DB_NAME +
                        "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                USER,
                PASSWORD
        );
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            //initializeDatabase(conn);
            return conn.isValid(2);
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }

    private static void initializeDatabase(Connection conn) throws SQLException {
        createDefaultAdminIfNotExists(conn);
    }

    private static void createDefaultAdminIfNotExists(Connection conn) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE UserName = 'admin'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            rs.next();
            if (rs.getInt(1) == 0) {
                String salt = PasswordUtil.generateSalt();
                String passwordHash = PasswordUtil.hashPassword("admin123", salt);

                String insertSql = "INSERT INTO Users (UserName, Password, Salt, FirstName, LastName, RoleID) " +
                        "VALUES (?, ?, ?, ?, ?, (SELECT RoleID FROM Roles WHERE RoleName = 'admin'))";

                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, "admin");
                    pstmt.setString(2, passwordHash);
                    pstmt.setString(3, salt);
                    pstmt.setString(4, "System");
                    pstmt.setString(5, "Administrator");

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Default admin account created successfully");
                    }
                }
            } else {
                System.out.println("Admin account already exists");
            }
        }
    }
}