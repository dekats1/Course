package com.salon.Server.Services.Admin.Response;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Manager;
import com.salon.Server.Utils.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerResponse {
    public static List<Manager> takeAllManagers() {
        List<Manager> managers = new ArrayList<>();
        String query = "SELECT u.UserName, u.FirstName AS name, u.LastName AS lastName, "
                + "COUNT(s.ReportID) AS reportCount, u.dateAt AS registrationDate "
                + "FROM Users u LEFT JOIN Reports s ON u.UserName = s.UserName "
                + "WHERE u.RoleID = (SELECT RoleID FROM Roles WHERE RoleName = 'manager') "
                + "GROUP BY u.UserName, u.FirstName, u.LastName, u.dateAt "
                + "ORDER BY reportCount DESC";

        try (Connection conn = DataBaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Manager manager = new Manager(rs.getString("UserName"), rs.getString("name"), rs.getString("lastName"), rs.getInt("reportCount"), rs.getString("registrationDate"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return managers;
    }

    public static AdminRequest addManager(Manager manager) {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
        String insertSql = "INSERT INTO Users (UserName, Password, Salt, FirstName, LastName, RoleID, dateAt) "
                + "VALUES (?, ?, ?, ?, ?, (SELECT RoleID FROM Roles WHERE RoleName = 'manager'), ?)";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, manager.getUserName());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String salt = PasswordUtil.generateSalt();
                    String passwordHash = PasswordUtil.hashPassword(manager.getPassword(), salt);

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, manager.getUserName());
                        insertStmt.setString(2, passwordHash);
                        insertStmt.setString(3, salt);
                        insertStmt.setString(4, manager.getName());
                        insertStmt.setString(5, manager.getLastName());
                        insertStmt.setString(6, manager.getDateAt());

                        int affectedRows = insertStmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Manager account created successfully");
                        } else {
                            System.out.println("Failed to create manager account");
                            return new AdminRequest(false,"Ошибка при добавлении пользователя");
                        }
                    }
                } else {
                    System.out.println("Manager account already exists");
                    return new AdminRequest(false,"Пользователь с данный логином занят");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding manager: " + e.getMessage(), e);
        }
        return new AdminRequest(true,"");
    }

    public static boolean delManager(String username) {
        if (!isManager(username)) {
            System.out.println("Пользователь не является менеджером");
            return false;
        }

        String deleteReportsSql = "DELETE FROM Reports WHERE UserName = ?";

        String deleteUserSql = "DELETE FROM Users WHERE UserName = ?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Начинаем транзакцию

            try (PreparedStatement deleteReportsStmt = conn.prepareStatement(deleteReportsSql);
                 PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql)) {

                deleteReportsStmt.setString(1, username);
                deleteReportsStmt.executeUpdate();

                deleteUserStmt.setString(1, username);
                int affectedRows = deleteUserStmt.executeUpdate();

                conn.commit();
                return affectedRows > 0;

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка при удалении менеджера: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }

    private static boolean isManager(String username) {
        String sql = "SELECT COUNT(*) FROM Users u " +
                "JOIN Roles r ON u.RoleID = r.RoleID " +
                "WHERE u.UserName = ? AND r.RoleName = 'manager'";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки роли: " + e.getMessage(), e);
        }
    }
}
