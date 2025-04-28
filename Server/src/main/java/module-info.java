module com.salon.Server {
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.base;
    requires jdk.jdi;
    exports com.salon.Server.Services;  // Экспортируем необходимые классы
    exports com.salon.Server.Services.Export;
    exports com.salon.Server.Services.Admin;
    exports com.salon.Server.Services.Seller;
    exports com.salon.Server.Services.Manager;
    exports com.salon.Server.Services.Manager.Service;
}