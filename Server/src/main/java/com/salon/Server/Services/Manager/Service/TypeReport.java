package com.salon.Server.Services.Manager.Service;

public enum TypeReport {
    PRODUCT("Отчет по товару"),
    SELLER("Отчет по продавцу");

    private String report;

    TypeReport(String report) {
        this.report = report;
    }
    @Override
    public String toString() {
        return report;
    }
}