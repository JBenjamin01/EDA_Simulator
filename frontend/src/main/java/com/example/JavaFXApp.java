package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("¡Hola desde JPro + JavaFX!");
        btn.setOnAction(e -> btn.setText("¡Click recibido!"));

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Demo JPro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
