package com.eda.frontend;

import com.jpro.webapi.JProApplication;
import com.eda.frontend.views.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends JProApplication {
    @Override
    public void start(Stage stage) {
        MainView root = new MainView();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Simulador de Estructuras de Datos");
        stage.setScene(scene);
        stage.show();
    }
}
