package com.jms.salon.Controllers.Manager;

import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Product;
import com.salon.Server.Services.Export.ReportSale;
import com.salon.Server.Services.Manager.ManagerRequest;
import com.salon.Server.Services.Manager.Service.TypeReport;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportController implements Initializable {

    @FXML
    private ToggleButton sellerReportBtn;
    @FXML
    private ToggleButton productReportBtn;
    @FXML
    private ComboBox<String> sellerComboBox;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> productComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private VBox sellerSelectionBox;
    @FXML
    private VBox productSelectionBox;
    @FXML
    private VBox resultBox;
    @FXML
    private TextArea reportContent;
    @FXML
    private Text reportTitle;
    @FXML
    private Button generateBtn;
    @FXML
    private Button saveBtn;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupToggleGroup();
        initializeDatePickers();
        loadInitialData();
        setupListeners();

        generateBtn.setOnAction(event -> {
            generateReport();
        });
        saveBtn.setOnAction(event -> {
            saveReport();
        });
    }

    private void setupToggleGroup() {
        ToggleGroup reportTypeGroup = new ToggleGroup();
        sellerReportBtn.setToggleGroup(reportTypeGroup);
        productReportBtn.setToggleGroup(reportTypeGroup);
        sellerReportBtn.setSelected(true);
    }

    private void initializeDatePickers() {
        startDatePicker.setValue(LocalDate.now().minusMonths(1));
        endDatePicker.setValue(LocalDate.now());
    }

    private void loadInitialData() {
        loadSellers();
        loadCategoriesAndProducts();
    }

    private void loadSellers() {
        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("AllSellers"));
        List<String> sellers = (List<String>) Model.getInstance().getConnectionServer().receiveObject();
        sellerComboBox.setItems(FXCollections.observableArrayList(sellers));
    }

    private void loadCategoriesAndProducts() {
        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("AllProducts"));
        Model.getInstance().getProducts().setAll((List<Product>) Model.getInstance().getConnectionServer().receiveObject());

        Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("AllCategories"));
        Product.setCategories((List<String>) Model.getInstance().getConnectionServer().receiveObject());
        categoryComboBox.getItems().setAll(Product.getCategories());
    }

    private void setupListeners() {
        sellerReportBtn.selectedProperty().addListener((obs, oldVal, newVal) -> {
            reportContent.clear();
            sellerSelectionBox.setVisible(newVal);
            sellerSelectionBox.setManaged(newVal);
            productSelectionBox.setVisible(!newVal);
            productSelectionBox.setManaged(!newVal);
        });

        categoryComboBox.valueProperty().addListener((obs, oldVal, selectedCategory) -> {
            if (selectedCategory != null) {
                List<String> products = Model.getInstance().getProducts().stream()
                        .filter(p -> p.getCategory().equals(selectedCategory))
                        .map(Product::getName)
                        .collect(Collectors.toList());
                productComboBox.setItems(FXCollections.observableArrayList(products));
                productComboBox.setDisable(false);
            }
        });
    }

    private void generateReport() {
        if (!validateInput()) {
            showAlert("Ошибка", "Заполните все необходимые поля");
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (sellerReportBtn.isSelected()) {
            generateSellerReport(startDate, endDate);
        } else {
            generateProductReport(startDate, endDate);
        }
    }

    private void generateSellerReport(LocalDate startDate, LocalDate endDate) {
        String sellerName = sellerComboBox.getValue();

        Model.getInstance().getConnectionServer().sendObject(
                new ManagerRequest("SellerReport", sellerName, startDate, endDate)
        );
        ReportSale sellerReport = (ReportSale) Model.getInstance().getConnectionServer().receiveObject();

        String period = startDate.format(dateFormatter) + " - " + endDate.format(dateFormatter);
        String report = String.format(
                "Отчет по продавцу: %s\n" +
                        "Период: %s\n\n" +
                        "Количество заказов: %d\n" +
                        "Общая сумма продаж: %.2f руб.\n" +
                        "Прибыль: %.2f руб.",
                sellerName, period, sellerReport.getTotalAmount(),
                sellerReport.getTotalPrice(), sellerReport.getTotalProfit()
        );

        displayReport("Отчет по продавцу: " + sellerName, report);
    }

    private void generateProductReport(LocalDate startDate, LocalDate endDate) {
        String productName = productComboBox.getValue();

        Model.getInstance().getConnectionServer().sendObject(
                new ManagerRequest("ProductReport", productName, startDate, endDate)
        );
        ReportSale productReport = (ReportSale) Model.getInstance().getConnectionServer().receiveObject();

        String period = startDate.format(dateFormatter) + " - " + endDate.format(dateFormatter);
        String report = String.format(
                "Отчет по товару: %s\n" +
                        "Период: %s\n\n" +
                        "Количество продаж: %d\n" +
                        "Общая выручка: %.2f руб.\n" +
                        "Общая прибыль: %.2f руб.",
                productName, period, productReport.getTotalAmount(),
                productReport.getTotalPrice(), productReport.getTotalProfit()
        );

        displayReport("Отчет по товару: " + productName, report);
    }

    private void displayReport(String title, String content) {
        reportTitle.setText(title);
        reportContent.setText(content);
        resultBox.setVisible(true);
    }

    private boolean validateInput() {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            return false;
        }

        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showAlert("Ошибка", "Дата начала не может быть позже даты окончания");
            return false;
        }

        if (sellerReportBtn.isSelected()) {
            return sellerComboBox.getValue() != null;
        } else {
            return categoryComboBox.getValue() != null &&
                    productComboBox.getValue() != null;
        }
    }

    private void saveReport() {
        try {
            String currentUser = Model.getInstance().getCurrentUser();
            TypeReport report = sellerReportBtn.isSelected() ? TypeReport.SELLER : TypeReport.PRODUCT;

            Model.getInstance().getConnectionServer().sendObject(new ManagerRequest("SaveReport",
                    report,
                    reportContent.getText(),
                    currentUser
            ));
            Boolean success = (Boolean) Model.getInstance().getConnectionServer().receiveObject();
            if (success) {
                showAlert("Успех", "Отчет успешно сохранен");
            } else {
                showAlert("Ошибка", "Не удалось сохранить отчет");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Произошла ошибка при сохранении отчета: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}