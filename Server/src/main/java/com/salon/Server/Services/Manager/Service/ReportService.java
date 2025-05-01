package com.salon.Server.Services.Manager.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.ReportSale;
import com.salon.Server.Services.Export.Sale;
import com.salon.Server.Services.Export.Seller;
import com.salon.Server.Services.LogService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    public static List<Seller> takeAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        String query = "SELECT u.UserName, u.FirstName AS name, u.LastName AS lastName, " +
                "COUNT(s.SaleID) AS sellCount, u.dateAt AS registrationDate " +
                "FROM Users u LEFT JOIN Sales s ON u.UserName = s.SellerUserName " +
                "WHERE u.RoleID = (SELECT RoleID FROM Roles WHERE RoleName = 'Seller') " +
                "GROUP BY u.UserName, u.FirstName, u.LastName, u.dateAt " +
                "ORDER BY sellCount DESC";

        try (Connection conn = DataBaseConnection.getConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Seller seller = new Seller(rs.getString("UserName"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getInt("sellCount"),
                        rs.getString("registrationDate"));
                sellers.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellers;
    }


    public static ReportSale sellerReport(String userName, LocalDate startDate, LocalDate endDate) {
        ReportSale report = new ReportSale();
        List<Sale> sales = new ArrayList<>();

        String sql = "SELECT sd.Quantity, sd.SalePrice, p.CostPrice " +
                "FROM SaleDetails sd " +
                "JOIN Products p ON sd.ProductID = p.ProductID " +
                "JOIN Sales s ON sd.SaleID = s.SaleID " +
                "WHERE s.SellerUserName = ? AND s.SaleDate BETWEEN ? AND ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userName);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate.plusDays(1)));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setQuantity(rs.getInt("Quantity"));
                sale.setPrice(rs.getDouble("SalePrice"));
                sale.setCost(rs.getDouble("CostPrice"));
                sales.add(sale);
            }

            ReportSale.setSales(sales);
            report.calculation();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    public static ReportSale productReport(String productName, LocalDate startDate, LocalDate endDate) {
        ReportSale report = new ReportSale();
        List<Sale> sales = new ArrayList<>();

        String sql = "SELECT sd.Quantity, sd.SalePrice, p.CostPrice " +
                "FROM SaleDetails sd " +
                "JOIN Products p ON sd.ProductID = p.ProductID " +
                "JOIN Sales s ON sd.SaleID = s.SaleID " +
                "WHERE p.ProductName = ? AND s.SaleDate BETWEEN ? AND ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productName);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate.plusDays(1)));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setQuantity(rs.getInt("Quantity"));
                sale.setPrice(rs.getDouble("SalePrice"));
                sale.setCost(rs.getDouble("CostPrice"));
                sales.add(sale);
            }

            ReportSale.setSales(sales);
            report.calculation();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    public static boolean saveReport(String reportTitle, String reportContent, String userName, String ownerName) {
        String sql = "INSERT INTO Reports (ReportType, ReportData, UserName) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reportTitle);
            stmt.setString(2, reportContent);
            stmt.setString(3, userName);

            int rowsAffected = stmt.executeUpdate();
            LogService.addLog(ownerName,  "Добавил отчёт");

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
