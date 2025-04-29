package com.jms.salon.Controllers.Manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportHistoryCellController implements Initializable {

    @FXML
    private Text reportAuthor;
    @FXML
    private Label reportDataLabel;
    @FXML
    private Text reportDate;
    @FXML
    private Text reportTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setInfo(String author, String date, String title, String content) {
        reportAuthor.setText(author);
        reportDate.setText(date);
        reportTitle.setText(title);
        reportDataLabel.setText(content);
    }

}
