package com.salon.Server.Services.Product;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsService {
    public static List<Product> takeAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.productID, p.ProductName, p.Description, " +
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
                        rs.getInt("productID"),
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

    public static AdminRequest addProduct(Product product) {
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
                            return new AdminRequest(false, "Не удалось добавить продукт");

                        }
                    }
                } else {
                    System.out.println("Продукт с таким названием уже существует");
                    return new AdminRequest(false, "Продукт с таким названием уже существует");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении продукта", e);
        }
        return new AdminRequest(true, "");
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


    public static void editProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getProductId() <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        try (Connection connection = DataBaseConnection.getConnection()) {

            try {
                int categoryId = getCategoryId(connection, product.getCategory());

                String updateSql = "UPDATE Products SET ProductName = ?, Description = ?, "
                        + "SalePrice = ?, CostPrice = ?, StockLevel = ?, CategoryID = ? "
                        + "WHERE ProductID = ?";

                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, product.getName());
                    updateStmt.setString(2, product.getDescription());
                    updateStmt.setDouble(3, product.getPrice());
                    updateStmt.setDouble(4, product.getCost());
                    updateStmt.setInt(5, product.getQuantity());
                    updateStmt.setInt(6, categoryId);
                    updateStmt.setInt(7, product.getProductId());

                    if (updateStmt.executeUpdate() == 0) {
                        throw new RuntimeException("Product with ID " + product.getProductId() + " not found");
                    }
                }

                System.out.println("Product with ID " + product.getProductId() + " updated");
            } catch (SQLException e) {
                throw new RuntimeException("Failed to update product", e);  // Пробрасываем исключение дальше
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error", e);
        }
    }

    private static int getCategoryId(Connection connection, String categoryName) throws SQLException {
        String sql = "SELECT CategoryID FROM Category WHERE CategoryName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("CategoryID");
                }
                throw new SQLException("Category '" + categoryName + "' not found");
            }
        }
    }
}