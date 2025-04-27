package com.salon.Server.Services.Manager;

import com.salon.Server.Services.Export.Report;

import java.io.Serializable;
import java.util.Date;

public class ManagerRequest implements Serializable {
    private final String request;
    private final String userName;
    private  String password;
    private String newPassword;
    private String photoPath;
    private Boolean success;
    private Date date;
    private Report report;
    private String errorMessage;

    public ManagerRequest(String request) {
        this.request = request;
        this.userName = null;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.date = null;
        this.report = null;
        this.errorMessage = null;
    }

    public ManagerRequest(String request, String userName) {
        this.request = request;
        this.userName = userName;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.date = null;
        this.report = null;
    }

    public ManagerRequest(boolean success, String errorMessage) {
        this.request = null;
        this.userName = null;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = success;
        this.date = null;
        this.report = null;
        this.errorMessage = errorMessage;
    }

    public ManagerRequest(String userName, Date date) {
        this.request = null;
        this.userName = userName;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.date = date;
        this.report = null;
        this.errorMessage = null;
    }

    public ManagerRequest(String request, String userName, String password, String newPassword) {
        this.request = request;
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
        this.photoPath = null;
        this.success = null;
        this.date = null;
        this.report = null;
        this.errorMessage = null;
    }

    public ManagerRequest(String request, String userName, String photoPath) {
        this.request = request;
        this.userName = userName;
        this.password = null;
        this.newPassword = null;
        this.photoPath = photoPath;
        this.success = null;
        this.date = null;
        this.report = null;
        this.errorMessage = null;
    }


    public String getRequest() {
        return request;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public String getPhotoPath() {
        return photoPath;
    }
    public Boolean getSuccess() {
        return success;
    }
    public Date getDate() {
        return date;
    }
    public Report getReport() {
        return report;
    }

    public String getErrorMassage() {
        return errorMessage;
    }
}
