package com.jms.salon;

import com.jms.salon.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Locale;

public class App extends Application {
    @Override
    public void start(Stage stage){
        Locale.setDefault(Locale.US);
        Model.getInstance().getConnectionServer();
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}
//1916