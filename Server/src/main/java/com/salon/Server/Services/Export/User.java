package com.salon.Server.Services.Export;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String name;
    private String lastName;
    private String dateAt;
    private String password;

    public User() {}

    public User(String userName, String name, String lastName, String dateAt) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.dateAt = dateAt;
    }
    public User(String userName, String name, String lastName, String password, String dateAt) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.dateAt = dateAt;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDateAt() { return dateAt; }
    public void setDateAt(String dateAt) { this.dateAt = dateAt; }
    public String getPassword() { return password; }
}
