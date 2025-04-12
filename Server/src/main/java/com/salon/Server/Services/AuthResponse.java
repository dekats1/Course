package com.salon.Server.Services;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    private final boolean success;
    private final String role;
    private final String message;

    public AuthResponse(boolean success, String role, String message) {
        this.success = success;
        this.role = role;
        this.message = message;
    }

    // Геттеры
    public boolean isSuccess() { return success; }
    public String getRole() { return role; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "success=" + success +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}