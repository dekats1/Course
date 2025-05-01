package com.salon.Server.Services.Manager.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Sale;
import com.salon.Server.Services.Export.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsService {
    public static Sale takeProductByCount() {
        String sql = "SELECT p.ProductID, p.ProductName, SUM(sd.Quantity) as TotalQuantity " +
                "FROM Products p " +
                "JOIN SaleDetails sd ON p.ProductID = sd.ProductID " +
                "GROUP BY p.ProductID, p.ProductName " +
                "ORDER BY TotalQuantity DESC " +
                "LIMIT 1";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sale sale = new Sale(
                rs.getString("ProductName"),
                rs.getInt("TotalQuantity"));
                return sale;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Sale takeProductByProfit() {
        String sql = "SELECT p.ProductID, p.ProductName, " +
                "SUM(sd.Quantity * (sd.SalePrice - p.CostPrice)) as TotalProfit " +
                "FROM Products p " +
                "JOIN SaleDetails sd ON p.ProductID = sd.ProductID " +
                "GROUP BY p.ProductID, p.ProductName " +
                "ORDER BY TotalProfit DESC " +
                "LIMIT 1";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sale sale = new Sale(
                rs.getString("ProductName"),
                rs.getDouble("TotalProfit"));
                return sale;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Seller takeBestSellerByCount() {
        String sql = "SELECT u.UserName, u.FirstName, u.LastName, COUNT(s.SaleID) as SalesCount " +
                "FROM Users u " +
                "JOIN Sales s ON u.UserName = s.SellerUserName " +
                "WHERE u.RoleID = (SELECT RoleID FROM Roles WHERE RoleName = 'Seller') " +
                "GROUP BY u.UserName, u.FirstName, u.LastName " +
                "ORDER BY SalesCount DESC " +
                "LIMIT 1";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Seller seller = new Seller();
                seller.setUserName(rs.getString("UserName"));
                seller.setName(rs.getString("FirstName"));
                seller.setLastName(rs.getString("LastName"));
                seller.setSellCount(rs.getInt("SalesCount"));
                return seller;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
