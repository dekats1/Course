package com.salon.Server.Services.Manager;

import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.Report;
import com.salon.Server.Services.Manager.Service.TypeReport;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class ManagerRequest implements Serializable {
    private final String request;
    private final String userName;
    public int getId;
    private  String password;
    private String newPassword;
    private String photoPath;
    private Boolean success;
    private Date date;
    private Report report;
    private String message;
    private Product product;
    private LocalDate startDate;
    private LocalDate endDate;
    private TypeReport reportType;
    private int id;

    public ManagerRequest(String request) {
        this.request = request;
        this.userName = null;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.date = null;
        this.report = null;
        this.message = null;
    }

    public ManagerRequest(String request, Product product) {
        this.request = request;
        this.userName = null;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.date = null;
        this.report = null;
        this.message = null;
        this.product = product;
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
        this.message = errorMessage;
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
        this.message = null;
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
        this.message = null;
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
        this.message = null;
    }

    public ManagerRequest(String request, String userName, LocalDate startDate, LocalDate endDate) {
        this.request = request;
        this.userName = userName;
        this.password = null;
        this.newPassword = null;
        this.photoPath = null;
        this.success = null;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ManagerRequest(String request, TypeReport typeReport, String message, String currentUser) {
        this.request = request;
        this.reportType = typeReport;
        this.message = message;
        this.userName = currentUser;
        this.success = null;
        this.date = null;
        this.report = null;
    }

    public ManagerRequest(String request, int id) {
        this.request = request;
        this.userName = null;
        this.reportType = null;
        this.product = null;
        this.success = null;
        this.date = null;
        this.report = null;
        this.message = null;
        this.id = id;
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
        return message;
    }
    public Product getProduct() {
        return product;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public TypeReport getReportType() {
        return reportType;
    }
    public int getId() {
        return id;
    }
}
