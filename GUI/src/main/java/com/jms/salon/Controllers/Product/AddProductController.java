package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.Model;
import com.jms.salon.Models.Product;
import com.jms.salon.Views.AdminMenuOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
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
        categoryComboBox.getItems().addAll(
                "Электроника",
                "Одежда",
                "Продукты",
                "Книги",
                "Другое"
        );
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
            Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
        });

        saveButton.setOnAction(event -> {
            if (validateFields()) {
                Product product = createProductFromFields();
                Model.getInstance().addProduct(product);
                Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
                clearFields();
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