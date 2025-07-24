package com.eda.frontend.views;

import com.eda.frontend.components.BTreeNodeView;
import com.eda.frontend.tree.BTree;
import com.eda.frontend.tree.BTreeNode;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class BTreeView extends VBox {

    private BTree tree;
    private final Pane canvas;
    private final ComboBox<Integer> degreeSelector;

    public BTreeView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Simulador de Árbol B");
        title.setFont(new Font("Arial", 24));

        TextField input = new TextField();
        input.setPromptText("Ingrese valor");
        input.setPrefWidth(150);

        degreeSelector = new ComboBox<>();
        for (int i = 2; i <= 5; i++) degreeSelector.getItems().add(i);
        degreeSelector.setValue(2); // valor por defecto

        Button insert = new Button("Insertar");
        Button create = new Button("Crear Árbol");

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(8), Insets.EMPTY)));
        controls.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(8), BorderWidths.DEFAULT)));
        controls.setSpacing(10);
        controls.setPadding(new Insets(10));

        Label gradoLabel = new Label("Grado:");
        gradoLabel.setFont(new Font("Arial", 14));

        controls.getChildren().addAll(gradoLabel, degreeSelector, input, insert, create);

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: gray; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(800);

        getChildren().addAll(title, controls, canvas);

        create.setOnAction(e -> {
            int degree = degreeSelector.getValue();
            tree = new BTree(degree);
            canvas.getChildren().clear();
        });

        insert.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                if (tree == null) {
                    showError("Debe crear el árbol primero");
                    return;
                }
                tree.insert(value);
                drawTree();
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });
    }

    private void drawTree() {
        canvas.getChildren().clear();
        if (tree != null && tree.getRoot() != null) {
            drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
        }
    }

    private void drawNode(BTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        BTreeNodeView nodeView = new BTreeNodeView(node.keys);
        nodeView.setLayoutX(x);
        nodeView.setLayoutY(y);

        double childX = x - offset * (node.children.size() - 1) / 2.0;
        double childY = y + 80;

        for (int i = 0; i < node.children.size(); i++) {
            BTreeNode child = node.children.get(i);
            double cx = childX + i * offset;

            Line line = new Line(x + 15 * node.keys.size(), y + 30, cx + 15 * child.keys.size(), childY);
            line.setStroke(Color.GRAY);
            canvas.getChildren().add(0, line); // Dibujar detrás del nodo

            drawNode(child, cx, childY, offset / 1.5);
        }

        FadeTransition ft = new FadeTransition(Duration.millis(500), nodeView);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        canvas.getChildren().add(nodeView);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
