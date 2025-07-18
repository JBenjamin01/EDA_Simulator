package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import javafx.animation.TranslateTransition;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class BinaryTreeView extends VBox {

    private final Pane canvas;

    public BinaryTreeView() {
        setSpacing(10);

        Label title = new Label("Simulador de Árbol Binario");
        TextField input = new TextField();
        input.setPromptText("Ingrese valor");

        Button insert = new Button("Insertar");
        Button delete = new Button("Eliminar");
        Button search = new Button("Buscar");

        HBox controls = new HBox(10, input, insert, delete, search);

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: gray; -fx-background-color: white;");
        canvas.setPrefHeight(400);

        getChildren().addAll(title, controls, canvas);

        insert.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                addAnimatedNode(value);
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });
    }

    private void addAnimatedNode(int value) {
        TreeNodeView node = new TreeNodeView(value);

        // Aparecerá desde arriba, en una posición aleatoria
        Random rand = new Random();
        double startX = rand.nextDouble() * (canvas.getWidth() - 40) + 20;
        double endY = 100 + rand.nextDouble() * 100;

        node.setTranslateX(startX);
        node.setTranslateY(-50); // Empieza arriba

        canvas.getChildren().add(node);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), node);
        transition.setToY(endY);
        transition.play();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Entrada inválida");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
