package com.jms.salon.Controllers.Product;

import com.jms.salon.Models.Model;
import com.jms.salon.Views.AdminMenuOption;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private TextField costField;
    @FXML
    private Label currentCategoryLabel;
    @FXML
    private Label currentCostLabel;
    @FXML
    private Label currentDescriptionLabel;
    @FXML
    private Label currentNameLabel;
    @FXML
    private Label currentPriceLabel;
    @FXML
    private Label currentQuantityLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCurrentValue();
        buttonAction();
        setupValidation();
    }

    private void setupValidation() {
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

    private void buttonAction() {
        saveButton.setOnAction(event -> {
            saveProduct();
            closeWindow();

        });

        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }


    private void saveProduct() {
        if (!nameField.getText().isEmpty()) {
            ProductController.getSelectedProduct().setName(nameField.getText().trim());
        }

        if (!descriptionArea.getText().isEmpty()) {
            ProductController.getSelectedProduct().setDescription(descriptionArea.getText().trim());
        }

        if (categoryCombo.getValue() != null) {
            ProductController.getSelectedProduct().setCategory(categoryCombo.getValue());
        }

        if (!costField.getText().isEmpty()) {
            ProductController.getSelectedProduct().setCost(Double.parseDouble(costField.getText().trim()));
        }

        if (!quantityField.getText().isEmpty()) {
            ProductController.getSelectedProduct().setQuantity(Integer.parseInt(quantityField.getText().trim()));
        }
        if (!priceField.getText().isEmpty()) {
            ProductController.getSelectedProduct().setPrice(Double.parseDouble(priceField.getText().trim()));
        }
        Model.getInstance().getProducts().set(Model.getInstance().getProducts().indexOf(ProductController.getSelectedProduct()), ProductController.getSelectedProduct());
        Product product = ProductController.getSelectedProduct();
        Model.getInstance().getConnectionServer().sendObject(new AdminRequest("EditProduct",ProductController.getSelectedProduct()));
    }

    private void closeWindow() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.Products);
    }


    private void initializeCurrentValue() {
        currentCategoryLabel.setText(ProductController.getSelectedProduct().getCategory());
        currentCostLabel.setText(String.valueOf(ProductController.getSelectedProduct().getCost()));
        currentDescriptionLabel.setText(ProductController.getSelectedProduct().getDescription());
        currentNameLabel.setText(ProductController.getSelectedProduct().getName());
        currentPriceLabel.setText(String.valueOf(ProductController.getSelectedProduct().getPrice()));
        currentQuantityLabel.setText(String.valueOf(ProductController.getSelectedProduct().getQuantity()));

        if (categoryCombo != null) {
            categoryCombo.getSelectionModel().select(ProductController.getSelectedProduct().getCategory());
        }
    }
}