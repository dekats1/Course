module com.salon.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jdi;
    requires com.salon.Server;

    opens com.jms.salon to javafx.fxml;
    opens com.jms.salon.Controllers to javafx.fxml;
    opens com.jms.salon.Controllers.Seller to javafx.fxml;
    opens com.jms.salon.Controllers.Admin to javafx.fxml;
    opens com.jms.salon.Controllers.Manager to javafx.fxml;
    opens com.jms.salon.Controllers.Product to javafx.fxml;

    exports com.jms.salon;
    exports com.jms.salon.Controllers;
    exports com.jms.salon.Controllers.Admin;
    exports com.jms.salon.Controllers.Seller;
    exports com.jms.salon.Controllers.Manager;
    exports com.jms.salon.Models;
    exports com.jms.salon.Views;
}