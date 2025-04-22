package com.jms.salon.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerSellController implements Initializable {

    @FXML private Label nameLbl;
    @FXML private Label surnameLbl;
    @FXML private Label loginLbl;
    @FXML private Label dateLbl;
    @FXML private Label countSellLbl;
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

    public void setSellerInfo(String name, String surname, String login, String date, int count) {
        nameLbl.setText(name);
        surnameLbl.setText(surname);
        loginLbl.setText(login);
        dateLbl.setText(date);
        countSellLbl.setText(String.valueOf(count));

    }

    public void setItemPane(AnchorPane pane) {
        this.itemPane = pane;
    }

    public void setDeleteAction(Runnable action) {
        this.deleteAction = action;
    }
}