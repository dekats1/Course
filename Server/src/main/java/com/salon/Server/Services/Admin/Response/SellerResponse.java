package com.salon.Server.Services.Admin.Response;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Utils.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerResponse {

    public static List<Seller> takeAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        String query = "SELECT u.UserName, u.FirstName AS name, u.LastName AS lastName, " + "COUNT(s.SaleID) AS sellCount, u.dateAt AS registrationDate " + "FROM Users u LEFT JOIN Sales s ON u.UserName = s.SellerUserName " + "WHERE u.RoleID = (SELECT RoleID FROM Roles WHERE RoleName = 'Seller') " + "GROUP BY u.UserName, u.FirstName, u.LastName, u.dateAt " + "ORDER BY sellCount DESC";

        try (Connection conn = DataBaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Seller seller = new Seller(rs.getString("UserName"), rs.getString("name"), rs.getString("lastName"), rs.getInt("sellCount"), rs.getString("registrationDate"));
                sellers.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellers;
    }

    public static void addSeller(Seller seller) {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
        String insertSql = "INSERT INTO Users (UserName, Password, Salt, FirstName, LastName, RoleID, dateAt) " +
                "VALUES (?, ?, ?, ?, ?, (SELECT RoleID FROM Roles WHERE RoleName = 'seller'), ?)";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, seller.getUserName());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String salt = PasswordUtil.generateSalt();
                    String passwordHash = PasswordUtil.hashPassword(seller.getPassword(), salt);

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, seller.getUserName());
                        insertStmt.setString(2, passwordHash);
                        insertStmt.setString(3, salt);
                        insertStmt.setString(4, seller.getName());
                        insertStmt.setString(5, seller.getLastName());
                        insertStmt.setString(6, seller.getDateAt());

                        int affectedRows = insertStmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Seller account created successfully");
                        } else {
                            System.out.println("Failed to create seller account");
                        }
                    }
                } else {
                    System.out.println("Seller account already exists");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding seller: " + e.getMessage(), e);
        }
    }

    public static boolean delSeller(String userName) {
        String deleteDetailsSql = "DELETE sd FROM saledetails sd " +
                "JOIN sales s ON sd.SaleID = s.SaleID " +
                "WHERE s.SellerUserName = ?";

        String deleteSalesSql = "DELETE FROM sales WHERE SellerUserName = ?";
        String deleteUserSql = "DELETE FROM users WHERE UserName = ?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Начинаем транзакцию

            try (PreparedStatement deleteDetailsStmt = conn.prepareStatement(deleteDetailsSql);
                 PreparedStatement deleteSalesStmt = conn.prepareStatement(deleteSalesSql);
                 PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql)) {

                // 1. Удаляем детали продаж
                deleteDetailsStmt.setString(1, userName);
                deleteDetailsStmt.executeUpdate();

                // 2. Удаляем сами продажи
                deleteSalesStmt.setString(1, userName);
                deleteSalesStmt.executeUpdate();

                // 3. Удаляем пользователя
                deleteUserStmt.setString(1, userName);
                int affectedRows = deleteUserStmt.executeUpdate();

                conn.commit(); // Подтверждаем транзакцию
                return affectedRows > 0;

            } catch (SQLException e) {
                conn.rollback(); // Откатываем при ошибке
                throw new RuntimeException("Error deleting seller and related data: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }
}