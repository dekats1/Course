package com.jms.salon;

import com.jms.salon.Models.ConnectionServer;
import com.jms.salon.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getConnectionServer();
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}