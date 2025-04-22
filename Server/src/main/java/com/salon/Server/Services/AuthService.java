package com.salon.Server.Services;


import com.salon.Server.BD.DataBaseConnection;
import com.salon.Server.Utils.PasswordUtil;

import java.sql.*;

public class AuthService {
    public AuthResponse authenticate(String username, String password) {
        String query = "SELECT password, salt, roleID FROM users WHERE username = ?";
        String roleQuery = "SELECT roleName FROM roles WHERE roleID = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String salt = rs.getString("salt");
                String roleID = rs.getString("roleID");

                if (PasswordUtil.checkPassword(password, storedHash, salt)) {
                    try (PreparedStatement roleStmt = conn.prepareStatement(roleQuery)) {
                        roleStmt.setString(1, roleID);
                        ResultSet roleRs = roleStmt.executeQuery();

                        if (roleRs.next()) {
                            String roleName = roleRs.getString("roleName");
                            return new AuthResponse(true, roleName, "Authentication successful");
                        }
                    }
                }
            }
            return new AuthResponse(false, null, "Invalid credentials");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return new AuthResponse(false, null, "Database error");
        }
    }

}