package com.jms.salon;

import com.jms.salon.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class App extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {
        sleep(2000);
        Locale.setDefault(Locale.US);
        Model.getInstance().getConnectionServer();
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}
//3400