package com.jms.salon.Controllers.Manager;

import com.jms.salon.Models.Model;
import com.salon.Server.Services.Export.Report;
import com.salon.Server.Services.Manager.ManagerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportHistoryController implements Initializable {

    @FXML
    private ListView<AnchorPane> reportListView;
    @FXML
    private ToggleButton sellerReportBtn;
    @FXML
    private ToggleButton productReportBtn;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button applyFilterBtn;
    @FXML
    private Button deleteBtn;

    private ObservableList<Report> allReports = FXCollections.observableArrayList();
    private List<Report> filteredReports = new FilteredList<>(allReports);
    private String currentFilterType = "seller";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDatePickers();
        setupToggleGroup();
        setupListeners();
        loadInitialReports();
        setUpButton();
    }

    private void setUpButton() {
        deleteBtn.setOnAction(e -> {
            deleteReport();
        });
        applyFilterBtn.setOnAction(e -> {
            applyFilters();
        });

    }

    private void initializeDatePickers() {
        startDatePicker.setValue(LocalDate.now().minusMonths(1));
        endDatePicker.setValue(LocalDate.now());
    }

    private void setupToggleGroup() {
        ToggleGroup toggleGroup = new ToggleGroup();
        sellerReportBtn.setToggleGroup(toggleGroup);
        productReportBtn.setToggleGroup(toggleGroup);
        sellerReportBtn.setSelected(true);
    }

    private void setupListeners() {
        sellerReportBtn.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                filteredReports = allReports.stream().filter(report -> report.getTitle().equals("Отчет по продавцу")).collect(Collectors.toList());
                refreshReportList(filteredReports);
            }
        });

        productReportBtn.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                filteredReports = allReports.stream().filter(report -> !report.getTitle().equals("Отчет по продавцу")).collect(Collectors.toList());
                refreshReportList(filteredReports);
            }
        });
    }

    private void loadInitialReports() {
        Model.getInstance().getConnectionServer().sendObject(
                new ManagerRequest("ReportsForUser", Model.getInstance().getCurrentUser())
        );
        List<Report> reports = (List<Report>) Model.getInstance().getConnectionServer().receiveObject();
        allReports.setAll(reports);
        refreshReportList(allReports);
    }


    private void refreshReportList(List<Report> reports) {
        reportListView.getItems().clear();
        for (Report report : reports) {
            addReportToView(report);
        }
    }

    private void addReportToView(Report report) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Manager/ReportHistoryCell.fxml"));
            AnchorPane reportCell = loader.load();

            ReportHistoryCellController controller = loader.getController();
            controller.setInfo(report.getAuthor(), report.getDate(), report.getTitle(), report.getContent());

            reportListView.getItems().add(reportCell);
        } catch (Exception e) {
            e.printStackTrace();
            //Model.getInstance().showAlert("Ошибка", "Не удалось загрузить отчет");
        }
    }


    private void applyFilters() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            //Model.getInstance().showAlert("Ошибка", "Выберите диапазон дат");
            return;
        }

        if (startDate.isAfter(endDate)) {
            //Model.getInstance().showAlert("Ошибка", "Дата начала не может быть позже даты окончания");
            return;
        }


        filteredReports = allReports.stream()
                .filter(report -> {
                    try {
                        String dateStr = report.getDate();
                        LocalDate reportDate = LocalDate.parse(
                                dateStr.substring(0, 10),
                                DateTimeFormatter.ISO_LOCAL_DATE
                        );
                        return !reportDate.isBefore(startDate) && !reportDate.isAfter(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .filter(report -> {
                    if (sellerReportBtn.isSelected()) {
                        return report.getTitle().startsWith("Отчет по продавцу");
                    } else {
                        return report.getTitle().startsWith("Отчет по товару");
                    }
                })
                .collect(Collectors.toList());

        refreshReportList(filteredReports);
    }

    private void deleteReport() {
        int selectedIndex = reportListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < filteredReports.size()) {
            Report selected = filteredReports.get(selectedIndex);

            // Отправка запроса на удаление
            Model.getInstance().getConnectionServer().sendObject(
                    new ManagerRequest("DeleteReport", selected.getId())
            );

            Boolean isDeleted = (Boolean) Model.getInstance().getConnectionServer().receiveObject();

            if (isDeleted != null && isDeleted) {
                // Удаление из локальных списков
                allReports.remove(selected);
                filteredReports.remove(selected);

                // Обновление отображения
                refreshReportList(filteredReports);

                //Model.getInstance().showAlert("Успех", "Отчет успешно удален");
            } else {
                //Model.getInstance().showAlert("Ошибка", "Не удалось удалить отчет");
            }
        } else {
            //Model.getInstance().showAlert("Ошибка", "Выберите отчет для удаления");
        }
    }

}