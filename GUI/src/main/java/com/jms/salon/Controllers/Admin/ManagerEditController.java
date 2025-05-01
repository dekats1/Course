package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Manager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerEditController implements Initializable {

    public Label errorLbl;
    @FXML
    private ListView<AnchorPane> managerListView;
    @FXML
    private TextField loginField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button addSellerBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
        connectionServer.sendObject(new AdminRequest("AllManagers"));

        addSellerBtn.setOnAction(event -> {
            errorLbl.setVisible(false);
            String login = loginField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String date = LocalDate.now().toString();
            connectionServer.sendObject(new AdminRequest("AddManager", new Manager(login, firstName, lastName, password, date),Model.getInstance().getCurrentUser()));
           AdminRequest res = (AdminRequest) connectionServer.receiveObject();
            if(res.getSuccess())
                addManager(new Manager(login, firstName, lastName, password, date));
            else {
                errorLbl.setText(res.getErrorMassage());
                errorLbl.setVisible(true);
            }
           
        });

        Model.getInstance().getManagers().setAll((List<Manager>) connectionServer.receiveObject());

        Model.getInstance().getManagers().addListener((javafx.collections.ListChangeListener.Change<? extends Manager> change) -> {
            refreshSellerList();
        });
        refreshSellerList();
    }

    private void refreshSellerList() {
        managerListView.getItems().clear();
        for (Manager manager : Model.getInstance().getManagers()) {
            addManager(manager);
        }
    }

    public void addManager(Manager manager) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ManagerСell.fxml"));
        AnchorPane managerCell;
        try {
            managerCell = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        ManagerСellController controller = loader.getController();
        controller.setManagerInfo(manager.getName(),manager.getLastName(),manager.getUserName(),manager.getDateAt(),manager.getReportCount());
        controller.setItemPane(managerCell);

        controller.setDeleteAction(() -> {
            FadeTransition ft = new FadeTransition(Duration.millis(300), managerCell);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            Model.getInstance().getConnectionServer().sendObject(new AdminRequest("DelManager", manager.getUserName(),Model.getInstance().getCurrentUser()));
            ft.setOnFinished(event -> managerListView.getItems().remove(managerCell));
            ft.play();
        });

        managerListView.getItems().add(managerCell);

        clearField();
    }

    private void clearField() {
        loginField.clear();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
    }
}
