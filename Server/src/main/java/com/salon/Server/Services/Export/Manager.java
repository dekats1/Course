package com.salon.Server.Services.Export;

public class Manager extends User {
    private int reportCount;


    public Manager() {}

    public Manager(String userName, String name, String lastName, int reportCount, String dateAt) {
        super(userName, name, lastName, dateAt);
        this.reportCount = reportCount;
    }
    public Manager(String userName, String name, String lastName, String password, String dateAt) {
        super(userName, name, lastName, password, dateAt);
    }

    public int getReportCount() { return reportCount; }
    public void setReportCount(int reportCount) { this.reportCount = reportCount; }


}
