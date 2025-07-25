package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.AVLTree;
import com.eda.frontend.tree.AVLTreeNode;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.*;

public class AVLTreeView extends VBox {

    private final Pane canvas;
    private final AVLTree tree;
    private final TextField input;
    private final Button insert, delete, search, save, load;
    private final TextArea logArea;

    public AVLTreeView() {
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ECF0F1;");

        tree = new AVLTree();

        Label title = new Label("Simulador de Árbol AVL");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: #2C3E50;");
        title.setMaxWidth(Double.MAX_VALUE);

        input = new TextField();
        input.setPromptText("Ingrese valor");
        input.setPrefWidth(160);
        input.setStyle("""
            -fx-font-size: 14px;
            -fx-background-radius: 8;
            -fx-border-radius: 8;
            -fx-border-color: #BDC3C7;
            -fx-padding: 8;
        """);

        insert = styledButton("Insertar");
        delete = styledButton("Eliminar");
        search = styledButton("Buscar");
        save = styledButton("Guardar");
        load = styledButton("Cargar");

        insert.setOnAction(e -> handleInsert());
        delete.setOnAction(e -> handleDelete());
        search.setOnAction(e -> handleSearch());
        save.setOnAction(e -> handleSave());
        load.setOnAction(e -> handleLoad());

        HBox controls = new HBox(10, input, insert, delete, search, save, load);
        controls.setPadding(new Insets(10));

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: #BDC3C7; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(900);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(120);
        logArea.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 13px;");

        getChildren().addAll(title, controls, canvas, logArea);
    }

    private Button styledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("""
            -fx-background-color: #3498DB;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            -fx-padding: 8 16;
        """);

        btn.setOnMouseEntered(e -> btn.setStyle("""
            -fx-background-color: #2980B9;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            -fx-padding: 8 16;
        """));
        btn.setOnMouseExited(e -> btn.setStyle("""
            -fx-background-color: #3498DB;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 8;
            -fx-cursor: hand;
            -fx-padding: 8 16;
        """));

        return btn;
    }

    private void handleInsert() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            logArea.clear();

            logStep("Iniciando inserción del valor " + value);
            tree.insert(value);
            drawTree();
            logStep("Valor insertado correctamente: " + value);
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            logArea.clear();

            logStep("Intentando eliminar valor: " + value);
            tree.delete(value);
            drawTree();
            logStep("Valor eliminado y árbol balanceado.");
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleSearch() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            logArea.clear();

            logStep("Iniciando búsqueda del valor " + value);
            AVLTreeNode foundNode = tree.search(value);
            if (foundNode != null) {
                logStep("Valor encontrado: " + value);
            } else {
                logStep("Valor no encontrado.");
            }
            drawTreeWithHighlight(value);
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleSave() {
        try {
            tree.saveToFile("avl_tree.ser");
            logStep("Árbol guardado exitosamente en 'avl_tree.ser'");
        } catch (IOException e) {
            showError("Error al guardar el árbol: " + e.getMessage());
        }
    }

    private void handleLoad() {
        try {
            tree.loadFromFile("avl_tree.ser");
            drawTree();
            logStep("Árbol cargado exitosamente desde 'avl_tree.ser'");
        } catch (IOException | ClassNotFoundException e) {
            showError("Error al cargar el árbol: " + e.getMessage());
        }
    }

    private void drawTree() {
        canvas.getChildren().clear();
        drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void drawNode(AVLTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        node.x = x;
        node.y = y;

        if (node.left != null) {
            double childX = x - offset;
            double childY = y + 80;
            drawNode(node.left, childX, childY, offset / 2);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line);
        }

        if (node.right != null) {
            double childX = x + offset;
            double childY = y + 80;
            drawNode(node.right, childX, childY, offset / 2);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line);
        }

        TreeNodeView nodeView = new TreeNodeView(node.value);
        nodeView.setTranslateX(x);
        nodeView.setTranslateY(y);
        canvas.getChildren().add(nodeView);

        FadeTransition ft = new FadeTransition(Duration.millis(300), nodeView);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void drawTreeWithHighlight(int target) {
        canvas.getChildren().clear();
        drawNodeWithHighlight(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4, target);
    }

    private void drawNodeWithHighlight(AVLTreeNode node, double x, double y, double offset, int target) {
        if (node == null) return;

        node.x = x;
        node.y = y;

        if (node.left != null) {
            double childX = x - offset;
            double childY = y + 80;
            drawNodeWithHighlight(node.left, childX, childY, offset / 2, target);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line);
        }

        if (node.right != null) {
            double childX = x + offset;
            double childY = y + 80;
            drawNodeWithHighlight(node.right, childX, childY, offset / 2, target);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line);
        }

        TreeNodeView nodeView = new TreeNodeView(node.value);
        if (node.value == target) {
            nodeView.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
        }
        nodeView.setTranslateX(x);
        nodeView.setTranslateY(y);
        canvas.getChildren().add(nodeView);

        FadeTransition ft = new FadeTransition(Duration.millis(300), nodeView);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void logStep(String message) {
        logArea.appendText(message + "\n");
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
