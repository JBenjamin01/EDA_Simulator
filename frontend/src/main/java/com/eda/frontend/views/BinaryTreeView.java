package com.eda.frontend.views;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BinaryTreeView extends VBox {

    public BinaryTreeView() {
        setSpacing(10);

        Label title = new Label("Simulador de Árbol Binario");
        TextField input = new TextField();
        input.setPromptText("Ingrese valor");

        Button insert = new Button("Insertar");
        Button delete = new Button("Eliminar");
        Button search = new Button("Buscar");

        HBox controls = new HBox(10, input, insert, delete, search);

        Pane canvas = new Pane();
        canvas.setStyle("-fx-border-color: gray; -fx-background-color: white;");
        canvas.setPrefHeight(400);

        getChildren().addAll(title, controls, canvas);
    }
}
