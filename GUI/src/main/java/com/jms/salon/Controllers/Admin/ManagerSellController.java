package com.jms.salon.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

import java.util.ResourceBundle;

public class ManagerSellController implements Initializable {

    @FXML private Label nameLbl;
    @FXML private Label surnameLbl;
    @FXML private Label loginLbl;
    @FXML private Label dateLbl;
    @FXML private Button deleteBtn;

    private AnchorPane itemPane;
    private Runnable deleteAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteBtn.setOnAction(event -> {
            if (deleteAction != null) {
                deleteAction.run();
            }
        });
    }

    public void setManagerInfo(String name, String surname, String login, String date) {
        nameLbl.setText(name);
        surnameLbl.setText(surname);
        loginLbl.setText(login);
        dateLbl.setText(date);
    }

    public void setItemPane(AnchorPane pane) {
        this.itemPane = pane;
    }

    public void setDeleteAction(Runnable action) {
        this.deleteAction = action;
    }
}
