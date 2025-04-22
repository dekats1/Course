package com.salon.Server.Services.Admin.Response;

import com.salon.Server.BD.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Profile {
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
}
