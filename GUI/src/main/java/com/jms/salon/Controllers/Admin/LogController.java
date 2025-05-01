package com.jms.salon.Controllers.Admin;

import com.jms.salon.Models.Model;
import com.salon.Server.Services.Admin.AdminRequest;
import com.salon.Server.Services.Export.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LogController implements Initializable {
    @FXML
    private TableColumn<Log, String> actionColumn;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<Log> logTable;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TableColumn<Log, String> statusColumn;
    @FXML
    private TableColumn<Log, Date> timeColumn;
    @FXML
    private TableColumn<Log, String> userColumn;

    private ObservableList<Log> logsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadLogs();
        setupDatePickers();
        setupFilterButton();
    }

    private void setupTableColumns() {
        // Настраиваем привязку столбцов к свойствам Log
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Форматирование даты
        timeColumn.setCellFactory(column -> new TableCell<Log, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Можно использовать SimpleDateFormat для красивого вывода
                }
            }
        });
    }

    private void loadLogs() {
        Model.getInstance().getConnectionServer().sendObject(new AdminRequest("GetLogs"));
        List<Log> logs = (List<Log>) Model.getInstance().getConnectionServer().receiveObject();
        logsList.setAll(logs);
        logTable.setItems(logsList);
    }

    private void setupDatePickers() {
        startDatePicker.setValue(LocalDate.now().minusDays(7));
        endDatePicker.setValue(LocalDate.now());
    }

    private void setupFilterButton() {
        filterButton.setOnAction(event -> applyFilters());
    }

    private void applyFilters() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            //Model.getInstance().showAlert("Ошибка", "Выберите диапазон дат");
            return;
        }

        if (startDate.isAfter(endDate)) {
           // Model.getInstance().showAlert("Ошибка", "Дата начала не может быть позже даты окончания");
            return;
        }

        // Фильтрация логов по дате
        ObservableList<Log> filteredLogs = logsList.filtered(log -> {
            LocalDate logDate = log.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return !logDate.isBefore(startDate) && !logDate.isAfter(endDate);
        });

        logTable.setItems(filteredLogs);
    }
}