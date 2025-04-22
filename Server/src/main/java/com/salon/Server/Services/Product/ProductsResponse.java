package com.salon.Server.Services.Product;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsResponse {
    public static List<Product> takeAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductName, p.Description, " +
                "c.CategoryName, p.SalePrice, p.CostPrice, p.StockLevel " +
                "FROM Products p " +
                "LEFT JOIN Category c ON p.CategoryID = c.CategoryID";

        List<String> allCategories = loadAllCategories();
        Product.setCategories(allCategories);

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getString("CategoryName"),
                        rs.getDouble("SalePrice"),
                        rs.getDouble("CostPrice"),
                        rs.getInt("StockLevel")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products from database", e);
        }

        return products;
    }

    private static List<String> loadAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT CategoryName FROM Category";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(rs.getString("CategoryName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return categories;
    }

    public static void addProduct(Product product) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE ProductName = ?";

        String insertSql = "INSERT INTO Products (ProductName, Description, CategoryID, SalePrice, CostPrice, StockLevel) " +
                "VALUES (?, ?, (SELECT CategoryID FROM Category WHERE CategoryName = ?), ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {

            checkStmt.setString(1, product.getName());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setString(1, product.getName());
                        insertStmt.setString(2, product.getDescription());
                        insertStmt.setString(3, product.getCategory());
                        insertStmt.setDouble(4, product.getPrice());
                        insertStmt.setDouble(5, product.getCost());
                        insertStmt.setInt(6, product.getQuantity());

                        int affectedRows = insertStmt.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Продукт успешно добавлен в базу данных");
                        } else {
                            System.out.println("Не удалось добавить продукт");
                        }
                    }
                } else {
                    System.out.println("Продукт с таким названием уже существует");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении продукта", e);
        }
    }

    public static boolean deleteProduct(String productName) {
        String deleteDetailsSql = "DELETE sd FROM SaleDetails sd " +
                "JOIN Products p ON sd.ProductID = p.ProductID " +
                "WHERE p.ProductName = ?";

        String deleteProductSql = "DELETE FROM Products WHERE ProductName = ?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteDetailsStmt = conn.prepareStatement(deleteDetailsSql);
                 PreparedStatement deleteProductStmt = conn.prepareStatement(deleteProductSql)) {

                // 1. Удляем связанные записи в SaleDetails
                deleteDetailsStmt.setString(1, productName);
                deleteDetailsStmt.executeUpdate();

                deleteProductStmt.setString(1, productName);
                int affectedRows = deleteProductStmt.executeUpdate();

                conn.commit();
                return affectedRows > 0;

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка при удалении продукта: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }
}