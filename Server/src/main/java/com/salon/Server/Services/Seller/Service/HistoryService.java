package com.salon.Server.Services.Seller.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryService {
    public static List<Sale> takeHistorySales(String userName) {
        String query = "SELECT " +
                "s.SaleID, " +
                "p.ProductName, " +
                "sd.Quantity, " +
                "sd.SalePrice, " +
                "DATE_FORMAT(s.SaleDate, '%Y-%m-%d %H:%i:%s') as FormattedDate, " +
                "s.SellerUserName " +
                "FROM Sales s " +
                "JOIN SaleDetails sd ON s.SaleID = sd.SaleID " +
                "JOIN Products p ON sd.ProductID = p.ProductID " +
                "WHERE s.SellerUserName = ? " +
                "ORDER BY s.SaleDate DESC";

        List<Sale> sales = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userName);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale(
                            rs.getInt("SaleID"),
                            rs.getString("ProductName"),
                            rs.getInt("Quantity"),
                            rs.getDouble("SalePrice"),
                            formatter.parse(rs.getString("FormattedDate")),
                            rs.getString("SellerUserName")
                    );
                    sales.add(sale);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }
}
