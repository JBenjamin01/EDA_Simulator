package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.AVLTree;
import com.eda.frontend.tree.AVLTreeNode;
import javafx.animation.FadeTransition;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class AVLTreeView extends VBox {

    private final Pane canvas;
    private final AVLTree tree;

    public AVLTreeView() {
        setSpacing(10);
        tree = new AVLTree();

        Label title = new Label("Simulador de Árbol AVL");
        TextField input = new TextField();
        input.setPromptText("Ingrese valor");

        Button insert = new Button("Insertar");
        Button delete = new Button("Eliminar");
        Button search = new Button("Buscar");

        HBox controls = new HBox(10, input, insert, delete, search);

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: gray; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(800);

        getChildren().addAll(title, controls, canvas);

        insert.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                tree.insert(value);
                drawTree();
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });

        delete.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                tree.delete(value);
                drawTree();
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });

        search.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                tree.search(value);
                drawTreeWithHighlight(value);
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });
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
            canvas.getChildren().add(0, line); // Línea detrás
        }

        if (node.right != null) {
            double childX = x + offset;
            double childY = y + 80;
            drawNode(node.right, childX, childY, offset / 2);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line); // Línea detrás
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
            canvas.getChildren().add(0, line); // Línea detrás
        }

        if (node.right != null) {
            double childX = x + offset;
            double childY = y + 80;
            drawNodeWithHighlight(node.right, childX, childY, offset / 2, target);
            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(0, line); // Línea detrás
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
