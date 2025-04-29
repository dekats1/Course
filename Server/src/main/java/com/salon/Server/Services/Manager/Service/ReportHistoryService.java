package com.salon.Server.Services.Manager.Service;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportHistoryService {
    public static List<Report> getReportHistory(String userName) {
        String sql = "SELECT ReportID, ReportType, GeneratedDate, ReportData FROM reports where userName=?";

        try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Report> reports = new ArrayList<Report>();
            while (resultSet.next()) {
                reports.add(new Report(
                        resultSet.getInt("ReportID"),
                        resultSet.getString("ReportType"),
                        resultSet.getString("GeneratedDate"),
                        userName,
                        resultSet.getString("ReportData")

                ));
            }
            return reports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean deleteReport(int id) {
        String sql = "DELETE FROM Reports WHERE ReportID = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
