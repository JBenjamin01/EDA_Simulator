package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.SplayTree;
import com.eda.frontend.tree.SplayTreeNode;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class SplayTreeView extends VBox {

    private final Pane canvas;
    private final SplayTree tree;
    private final TextField input;
    private final Button insert, delete, search;
    private final TextArea logArea; // <-- Nuevo

    public SplayTreeView() {
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ECF0F1;");

        tree = new SplayTree();

        Label title = new Label("Simulador de Árbol Splay");
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

        insert.setOnAction(e -> handleInsert());
        delete.setOnAction(e -> handleDelete());
        search.setOnAction(e -> handleSearch());

        HBox controls = new HBox(10, input, insert, delete, search);
        controls.setPadding(new Insets(10));

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: #BDC3C7; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(900);

        logArea = new TextArea(); // <-- Inicialización
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(120);
        logArea.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 13px;");

        getChildren().addAll(title, controls, canvas, logArea); // <-- Añadir log al layout
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
            logStep("Valor insertado (y splay aplicado si fue necesario): " + value);
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            logArea.clear();
            logStep("Iniciando eliminación del valor " + value);
            tree.delete(value);
            drawTree();
            logStep("Eliminación completada (y splay aplicado si fue necesario).");
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
            tree.search(value);
            drawTreeWithHighlight(value);
            logStep("Búsqueda completada. El valor fue splayeado si existía.");
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void drawTree() {
        canvas.getChildren().clear();
        drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void drawNode(SplayTreeNode node, double x, double y, double offset) {
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

    private void drawNodeWithHighlight(SplayTreeNode node, double x, double y, double offset, int target) {
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
            nodeView.setStyle("-fx-background-color: yellow; -fx-border-color: black; -fx-border-width: 2;");
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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Entrada inválida");
        alert.setContentText(message);
        alert.showAndWait();
    }
}