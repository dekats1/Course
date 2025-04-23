package com.jms.salon.Controllers.Product;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
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
    }

    private void initializeCurrentValue() {
        currentCategoryLabel.setText(ProductController.getProduct().getCategory());
        currentCostLabel.setText(String.valueOf(ProductController.getProduct().getCost()));
        currentDescriptionLabel.setText(ProductController.getProduct().getDescription());
        currentNameLabel.setText(ProductController.getProduct().getName());
        currentPriceLabel.setText(String.valueOf(ProductController.getProduct().getPrice()));
        currentQuantityLabel.setText(String.valueOf(ProductController.getProduct().getQuantity()));

    }
}
