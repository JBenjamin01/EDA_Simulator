package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.AVLTree;
import com.eda.frontend.tree.AVLTreeNode;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class AVLTreeView extends VBox {

    private final Pane canvas;
    private final AVLTree tree;
    private final TextField input;
    private final Button insert, delete, search;

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

        // Acciones de los botones
        insert.setOnAction(e -> handleInsert());
        delete.setOnAction(e -> handleDelete());
        search.setOnAction(e -> handleSearch());

        HBox controls = new HBox(10, input, insert, delete, search);
 
        controls.setPadding(new Insets(10));

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: #BDC3C7; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(900);

        getChildren().addAll(title, controls, canvas);
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
            tree.insert(value);
            drawTree();
            input.clear();
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(input.getText());
            tree.delete(value);
            drawTree();
            input.clear();
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleSearch() {
        try {
            int value = Integer.parseInt(input.getText());
            tree.search(value);
            drawTreeWithHighlight(value);
            input.clear();
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
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

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
