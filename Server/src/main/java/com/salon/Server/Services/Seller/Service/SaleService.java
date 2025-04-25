package com.salon.Server.Services.Seller.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Seller.SellerRequest;

import java.sql.*;

public class SaleService {
    public static SellerRequest makeSale(String userName, Product product, int quantity) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            connection.setAutoCommit(false); // Начинаем транзакцию

            // 0. Проверка доступного количества (перед началом транзакции)
            String checkStockSQL = "SELECT StockLevel FROM Products WHERE ProductID = ? FOR UPDATE";
            int currentStock;

            try (PreparedStatement checkStmt = connection.prepareStatement(checkStockSQL)) {
                checkStmt.setInt(1, product.getProductId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        return new SellerRequest(false, "Товар не найден");
                    }
                    currentStock = rs.getInt("StockLevel");

                    // Проверяем достаточно ли товара
                    if (currentStock < quantity) {
                        return new SellerRequest(false,
                                String.format("Недостаточно товара. Доступно: %d, запрошено: %d",
                                        currentStock, quantity));
                    }
                }
            }

            // 1. Создаем запись о продаже в таблице Sales
            String insertSaleSQL = "INSERT INTO Sales (SellerUserName, TotalAmount) VALUES (?, 0)";
            int saleId;

            try (PreparedStatement saleStmt = connection.prepareStatement(
                    insertSaleSQL, Statement.RETURN_GENERATED_KEYS)) {
                saleStmt.setString(1, userName);
                saleStmt.executeUpdate();

                try (ResultSet generatedKeys = saleStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        saleId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Не удалось получить ID продажи");
                    }
                }
            }

            // 2. Добавляем детали продажи в SaleDetails
            String insertDetailsSQL = """
            INSERT INTO SaleDetails 
                (SaleID, ProductID, Quantity, SalePrice) 
            VALUES (?, ?, ?, (
                SELECT SalePrice FROM Products WHERE ProductID = ?
            ))""";

            try (PreparedStatement detailsStmt = connection.prepareStatement(insertDetailsSQL)) {
                detailsStmt.setInt(1, saleId);
                detailsStmt.setInt(2, product.getProductId());
                detailsStmt.setInt(3, quantity);
                detailsStmt.setInt(4, product.getProductId());
                detailsStmt.executeUpdate();
            }

            // 3. Обновляем количество товара на складе с проверкой
            String updateStockSQL = """
            UPDATE Products 
            SET StockLevel = StockLevel - ? 
            WHERE ProductID = ? AND StockLevel >= ?""";

            try (PreparedStatement stockStmt = connection.prepareStatement(updateStockSQL)) {
                stockStmt.setInt(1, quantity);
                stockStmt.setInt(2, product.getProductId());
                stockStmt.setInt(3, quantity);

                int affectedRows = stockStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Не удалось обновить остатки. Возможно, товара стало недостаточно");
                }
            }

            // Фиксируем транзакцию
            connection.commit();

            // Логируем успешную продажу
            //logSale(connection, userName, saleId, product.getProductId(), quantity);

            return new SellerRequest(true, "Продажа успешно оформлена. ID: " + saleId);

        } catch (SQLException e) {
            // Откатываем транзакцию в случае ошибки
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }
            return new SellerRequest(false, "Ошибка при оформлении продажи: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
