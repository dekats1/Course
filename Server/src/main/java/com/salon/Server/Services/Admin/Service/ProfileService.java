package com.salon.Server.Services.Admin.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Utils.PasswordUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileService {
    public static String getPhoto(String userName) {
        final String sql = "SELECT ProfilePhotoPath FROM Users WHERE UserName = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getString("ProfilePhotoPath") : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get photo for user: " + userName, e);
        }
    }
    public static void updateUserPhoto(String userName, String photoPath) {
        final String sql = "UPDATE Users SET ProfilePhotoPath = ? WHERE UserName = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, photoPath);
            preparedStatement.setString(2, userName);

            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update photo for user: " + userName, e);
        }
    }

    public static AdminRequest changePassword(String userName, String oldPassword, String newPassword) {
        String sql = "SELECT Password, Salt FROM Users WHERE UserName = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String salt = resultSet.getString("Salt");
                String passwordHash = resultSet.getString("Password");

                if (PasswordUtil.checkPassword(oldPassword, passwordHash, salt)) {
                    String sql2 = "UPDATE Users SET Password = ? WHERE UserName = ?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
                        preparedStatement2.setString(1, PasswordUtil.hashPassword(newPassword, salt));
                        preparedStatement2.setString(2, userName);

                        int affectedRows = preparedStatement2.executeUpdate();
                        if (affectedRows == 0) {
                            return new AdminRequest(false, "Не удалось обновить пароль");
                        }
                        return new AdminRequest(true, "Пароль успешно изменён");
                    }
                } else {
                    return new AdminRequest(false, "Неверный текущий пароль");
                }
            } else {
                return new AdminRequest(false, "Пользователь не найден");
            }
        } catch (SQLException e) {
            return new AdminRequest(false, "Ошибка базы данных: " + e.getMessage());
        }
    }

    public static AdminRequest userData(String userName) {
        String sql = "SELECT FirstName, LastName, DateAt FROM Users WHERE UserName = ?";
        try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateAt = (Date) sdf.parse(resultSet.getString("DateAt"));
                return new AdminRequest(firstName+ " " + lastName, dateAt);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
