package com.eda.frontend.views;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.SplayTree;
import com.eda.frontend.tree.SplayTreeNode;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

public class SplayTreeView extends VBox {

    private final Pane canvas;
    private final SplayTree tree;

    public SplayTreeView() {
        setSpacing(10);
        tree = new SplayTree();

        Label title = new Label("Simulador de Árbol Splay");
        TextField input = new TextField();
        input.setPromptText("Ingrese valor");

        Button insert = new Button("Insertar");
        Button delete = new Button("Eliminar");
        

        HBox controls = new HBox(10, input, insert, delete);

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
                showError("Ingresa un numero valido bestia, digo bestie");
            }
        });
    }

    private void drawTree() {
        canvas.getChildren().clear();
        drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void drawNode(SplayTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        node.x = x;
        node.y = y;

        TreeNodeView nodeView = new TreeNodeView(node.value);
        nodeView.setTranslateX(x);
        nodeView.setTranslateY(y);
        canvas.getChildren().add(nodeView);

        if (node.left != null) {
            double childX = x - offset;
            double childY = y + 80;
            drawNode(node.left, childX, childY, offset / 2);

            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(line);
        }

        if (node.right != null) {
            double childX = x + offset;
            double childY = y + 80;
            drawNode(node.right, childX, childY, offset / 2);

            Line line = new Line(x + 20, y + 20, childX + 20, childY + 20);
            canvas.getChildren().add(line);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Entrada inválida");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
