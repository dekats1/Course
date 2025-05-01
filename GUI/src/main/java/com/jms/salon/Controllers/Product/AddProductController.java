package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.ManagerMenuOption;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Product;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Manager.ManagerRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public Label errorLbl;
    @FXML private Button cancelButton;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField costField;
    @FXML private TextArea descriptionField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCategoryComboBox();
        setupNumericFields();
        setupButtonActions();
    }

    private void setupCategoryComboBox() {
        categoryComboBox.getItems().addAll(Product.getCategories());
    }

    private void setupNumericFields() {
        // Валидация числовых полей
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                priceField.setText(oldValue);
            }
        });

        costField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                costField.setText(oldValue);
            }
        });

        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityField.setText(oldValue);
            }
        });
    }

    private void setupButtonActions() {
        cancelButton.setOnAction(event -> {
            clearFields();
            if (Model.getInstance().getCurrentRole().equals("admin")) {
                Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
            }else if (Model.getInstance().getCurrentRole().equals("manager")) {
                Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Products);
            }
        });

        saveButton.setOnAction(event -> {
            errorLbl.setVisible(false);
            if (validateFields()) {
                Product product = createProductFromFields();
               if (Model.getInstance().getCurrentRole().equals("admin")) {
                   Model.getInstance().getConnectionServer().sendObject(new AdminRequest("AddProduct", product,Model.getInstance().getCurrentUser()));
                   AdminRequest res = (AdminRequest) Model.getInstance().getConnectionServer().receiveObject();
                   if(res.getSuccess()) {
                       Model.getInstance().addProduct(product);
                       clearFields();
                       Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
                   }else{
                       errorLbl.setText(res.getErrorMassage());
                       errorLbl.setVisible(true);
                   }
                }
               else if (Model.getInstance().getCurrentRole().equals("manager")) {
                   Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("AddProduct", product,Model.getInstance().getCurrentUser()));
                   ManagerRequest res = (ManagerRequest) Model.getInstance().getConnectionServer().receiveObject();
                   if(res.getSuccess()) {
                       Model.getInstance().addProduct(product);
                       clearFields();
                       Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set(ManagerMenuOption.Products);
                   }else{
                       errorLbl.setText(res.getErrorMassage());
                       errorLbl.setVisible(true);
                   }
               }

            }
        });
    }

    private boolean validateFields() {
        if (nameField.getText().isEmpty() ||
                priceField.getText().isEmpty() ||
                costField.getText().isEmpty() ||
                quantityField.getText().isEmpty() ||
                categoryComboBox.getValue() == null) {

            showAlert("Ошибка", "Заполните все обязательные поля");
            return false;
        }

        try {
            Double.parseDouble(priceField.getText());
            Double.parseDouble(costField.getText());
            Integer.parseInt(quantityField.getText());
            return true;
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректные числовые значения");
            return false;
        }
    }

    private Product createProductFromFields() {
        return new Product(
                nameField.getText(),
                descriptionField.getText(),
                categoryComboBox.getValue(),
                Double.parseDouble(priceField.getText()),
                Double.parseDouble(costField.getText()),
                Integer.parseInt(quantityField.getText())
        );
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        priceField.clear();
        costField.clear();
        quantityField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}