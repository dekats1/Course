package com.salon.Server.Services.Export;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    String userName;
    String role;
    Date date;
    String message;

    public Log(String userName, String role, Date date, String message) {
        this.userName = userName;
        this.role = role;
        this.date = date;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }
    public String getRole() {
        return role;
    }
    public Date getDate() {
        return date;
    }
    public String getMessage() {
        return message; 
    }

}
