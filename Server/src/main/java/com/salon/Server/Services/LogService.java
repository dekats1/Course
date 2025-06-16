package com.salon.Server.Services;

import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Services.Export.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class LogService {
    public static void addLog(String userName, String message) {
        String role = getUserRole(userName);

        String sql = "INSERT INTO Logs (Owner, Status, LogData) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            String owner = (userName != null && !userName.isEmpty()) ? userName : "SYSTEM";

            stmt.setString(1, owner);
            stmt.setString(2, role);
            stmt.setString(3, message);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при записи лога: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getUserRole(String userName) {
        if (userName == null || userName.isEmpty()) {
            return null;
        }

        String sql = "SELECT r.RoleName FROM Users u " +
                "JOIN Roles r ON u.RoleID = r.RoleID " +
                "WHERE u.UserName = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("RoleName");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении роли пользователя: " + e.getMessage());
        }

        return null;
    }

    public static List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT Owner, Status, LogData, LogDate FROM Logs ORDER BY LogDate DESC";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String owner = rs.getString("Owner");
                String userName = rs.getString("Owner");
                String role = rs.getString("Status");

                if (owner != null && !owner.equals("SYSTEM")) {
                    int bracketIndex = owner.indexOf('(');
                    if (bracketIndex > 0) {
                        userName = owner.substring(0, bracketIndex).trim();
                        role = owner.substring(bracketIndex + 1, owner.indexOf(')'));
                    } else {
                        userName = owner;
                    }
                }

                LocalDateTime localDateTime = rs.getObject("LogDate", LocalDateTime.class);
// Конвертируем в Date через Instant
                Date logDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                String message = rs.getString("LogData");

                logs.add(new Log(userName, role, logDate, message));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении логов: " + e.getMessage());
            e.printStackTrace();
        }

        return logs;
    }

    public static List<Log> getLogs(Date startDate, Date endDate) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT Owner, Status, LogData, LogDate FROM Logs WHERE 1=1";

        // Динамически добавляем условия фильтрации
        if (startDate != null) {
            sql += " AND LogDate >= ?";
        }
        if (endDate != null) {
            sql += " AND LogDate <= ?";
        }
        sql += " ORDER BY LogDate DESC";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            if (startDate != null) {
                stmt.setTimestamp(paramIndex++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                stmt.setTimestamp(paramIndex++, new Timestamp(endDate.getTime()));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String owner = rs.getString("Owner");
                    String userName = parseUserName(owner);
                    String role = parseUserRole(owner);

                    logs.add(new Log(
                            userName,
                            role,
                            new Date(rs.getTimestamp("LogDate").getTime()),
                            rs.getString("LogData")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении логов: " + e.getMessage());
        }

        return logs;
    }

    private static String parseUserName(String owner) {
        if (owner == null || owner.equals("SYSTEM")) {
            return null;
        }
        int bracketIndex = owner.indexOf('(');
        return bracketIndex > 0 ? owner.substring(0, bracketIndex).trim() : owner;
    }

    private static String parseUserRole(String owner) {
        if (owner == null || !owner.contains("(")) {
            return null;
        }
        return owner.substring(owner.indexOf('(') + 1, owner.indexOf(')'));
    }
}
