package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Seller;
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

public class SellerEditController implements Initializable {
    @FXML
    public Label errorLbl;
    @FXML
    private ListView<AnchorPane> sellerListView;
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
        connectionServer.sendObject(new AdminRequest("AllSellers"));

        addSellerBtn.setOnAction(event -> {
            errorLbl.setVisible(false);
            String login = loginField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String date = LocalDate.now().toString();
            connectionServer.sendObject(new AdminRequest("AddSeller", new Seller(login, firstName, lastName, password, date),Model.getInstance().getCurrentUser()));
            AdminRequest res =(AdminRequest) connectionServer.receiveObject();
            if(res.getSuccess())
                addSeller(new Seller(login, firstName, lastName, password, date));
            else {
                errorLbl.setText(res.getErrorMassage());
                errorLbl.setVisible(true);
            }
        });


//        List<Seller> sel = new ArrayList<>();
//        Model.getInstance().getSellers().setAll(sel);
        Model.getInstance().getSellers().setAll((List<Seller>) connectionServer.receiveObject());

        Model.getInstance().getSellers().addListener((javafx.collections.ListChangeListener.Change<? extends Seller> change) -> {
            refreshSellerList();
        });
        refreshSellerList();
    }

    private void refreshSellerList() {
        sellerListView.getItems().clear();
        for (Seller seller : Model.getInstance().getSellers()) {
            addSeller(seller);
        }
    }

    public void addSeller(Seller seller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/SellerСell.fxml"));
        AnchorPane sellerCell;
        try {
            sellerCell = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SellerСellController controller = loader.getController();
        controller.setSellerInfo(seller.getName(), seller.getLastName(), seller.getUserName(), seller.getDateAt(), seller.getSellCount());
        controller.setItemPane(sellerCell);

        controller.setDeleteAction(() -> {
            FadeTransition ft = new FadeTransition(Duration.millis(300), sellerCell);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            Model.getInstance().getConnectionServer().sendObject(new AdminRequest("DelSeller", seller.getUserName(),Model.getInstance().getCurrentUser()));
            ft.setOnFinished(event -> Model.getInstance().getSellers().remove(sellerCell));
            ft.play();
        });

        sellerListView.getItems().add(sellerCell);

        clearField();
    }

    private void clearField() {
        loginField.clear();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
    }
}