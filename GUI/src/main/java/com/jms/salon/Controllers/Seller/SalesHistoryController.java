package com.jms.salon.Controllers.Seller;

import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Sale;
import com.salon.Server.Services.Seller.SellerRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SalesHistoryController implements Initializable {
    @FXML private DatePicker endDatePicker;
    @FXML private ListView<AnchorPane> salesListView;
    @FXML private TextField searchField;
    @FXML private DatePicker startDatePicker;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getSales().addListener((javafx.collections.ListChangeListener.Change<? extends Sale> change) -> {
            refreshFilteredSales();
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            refreshFilteredSales();
        });

        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            refreshFilteredSales();
        });

        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            refreshFilteredSales();
        });


        loadSalesData();
    }

    private void loadSalesData() {
        Model.getInstance().getConnectionServer().sendObject(
                new SellerRequest("HistorySales", Model.getInstance().getCurrentUser())
        );
        Model.getInstance().getSales().setAll(
                (List<Sale>) Model.getInstance().getConnectionServer().receiveObject()
        );
    }

    private void refreshFilteredSales() {
        List<Sale> filteredSales = Model.getInstance().getSales().stream()
                .filter(this::matchesSearchCriteria)
                .filter(this::matchesDateRange)
                .collect(Collectors.toList());

        refreshSaleList(filteredSales);
    }

    private boolean matchesSearchCriteria(Sale sale) {
        String searchText = searchField.getText().toLowerCase();
        return searchText.isEmpty() ||
                sale.getProductName().toLowerCase().contains(searchText);
    }

    private boolean matchesDateRange(Sale sale) {
        if (startDatePicker.getValue() == null && endDatePicker.getValue() == null) {
            return true;
        }

        try {
            Date saleDate = sale.getDate();
            if (saleDate == null) return false;

            String saleDateStr = dateFormat.format(saleDate);

            if (startDatePicker.getValue() != null) {
                String startDateStr = startDatePicker.getValue().toString();
                if (saleDateStr.compareTo(startDateStr) < 0) return false;
            }

            if (endDatePicker.getValue() != null) {
                String endDateStr = endDatePicker.getValue().toString();
                if (saleDateStr.compareTo(endDateStr) > 0) return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refreshSaleList(List<Sale> sales) {
        salesListView.getItems().clear();
        for (Sale sale : sales) {
            addSaleToView(sale);
        }
    }

    private void addSaleToView(Sale sale) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Seller/SalesHistoryCell.fxml"));
            AnchorPane saleCell = loader.load();

            SalesHistorySellController controller = loader.getController();
            if (controller != null) {
                controller.setInfo(sale);
                salesListView.getItems().add(saleCell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}