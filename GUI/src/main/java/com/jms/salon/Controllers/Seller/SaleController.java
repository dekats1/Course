package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Seller.SellerRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class SaleController implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button makeSaleBtn;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Label priceLabel;
    @FXML
    private ComboBox<String> productComboBox;
    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private Label totalLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionServer connectionServer = Model.getInstance().getConnectionServer();
        connectionServer.sendObject(new SellerRequest("AllProducts"));
        Model.getInstance().getProducts().setAll((List<Product>) connectionServer.receiveObject());
        connectionServer.sendObject(new SellerRequest("AllCategories"));
        Product.setCategories((List<String>) connectionServer.receiveObject());

        setupCategorySelectionListener();
        loadInitialCategories();
        setupQuantitySpinner();
        checkButton();
    }

    private void checkButton() {
        makeSaleBtn.setOnAction(event -> {

        });

        cancelBtn.setOnAction(event -> {
            loadInitialCategories();
        });
    }

    private void setupQuantitySpinner() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1);
        quantitySpinner.setValueFactory(valueFactory);


        quantitySpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantitySpinner.getEditor().setText(oldValue);
            }
        });

        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            updateTotalPrice();
        });
    }

    private void setupCategorySelectionListener() {
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newCategory) -> {
                    if (newCategory != null) {
                        updateProductsByCategory(newCategory);
                        updateTotalPrice();
                    }
                });
    }

    private void updateProductsByCategory(String category) {
        List<String> filteredProducts = Model.getInstance().getProducts().stream()
                .filter(product -> product.getCategory().equals(category))
                .map(Product::getName)
                .collect(Collectors.toList());

        productComboBox.getItems().setAll(filteredProducts);

        productComboBox.setPromptText("Выберите товар...");

        productComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateProductPrice(newVal);
                quantitySpinner.getValueFactory().setValue(0);
            }
        });
    }

    private void updateProductPrice(String productName) {
        Model.getInstance().getProducts().stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .ifPresent(product -> {
                    priceLabel.setText(String.format("%.2f", product.getPrice()));
                    updateTotalPrice();
                });
    }

    private void updateTotalPrice() {
        String selectedProduct = productComboBox.getValue();
        if (selectedProduct != null) {
            Model.getInstance().getProducts().stream()
                    .filter(p -> p.getName().equals(selectedProduct))
                    .findFirst()
                    .ifPresent(product -> {
                        int quantity = quantitySpinner.getValue();
                        double total = product.getPrice() * quantity;
                        totalLabel.setText(String.format("%.2f", total));
                    });
        } else {
            totalLabel.setText("0.00");
        }
    }

    private void loadInitialCategories() {
        productComboBox.setPromptText("Выберите товар...");
        productComboBox.setPlaceholder(new Label("Нет товаров в этой категории"));
        List<String> categories = Product.getCategories();

        categoryComboBox.getItems().setAll(categories);

        if (!categories.isEmpty()) {
            categoryComboBox.getSelectionModel().selectFirst();
        }
    }

}
