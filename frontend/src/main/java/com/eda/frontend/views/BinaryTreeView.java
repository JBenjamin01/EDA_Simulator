package com.eda.frontend.views;

import java.util.List;

import com.eda.frontend.components.TreeNodeView;
import com.eda.frontend.tree.BinaryTree;
import com.eda.frontend.tree.BinaryTreeNode;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class BinaryTreeView extends VBox {

    private final Pane canvas;
    private final BinaryTree tree;
    private final TextField input;
    private final Button insert, delete, search, inOrder;
    private final TextArea logArea;

    public BinaryTreeView() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #ECF0F1;");

        tree = new BinaryTree();

        Label title = new Label("Simulador de Árbol Binario");
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
        inOrder = styledButton("Recorrer InOrden");

        insert.setOnAction(e -> handleInsert());
        delete.setOnAction(e -> handleDelete());
        search.setOnAction(e -> handleSearch());
        inOrder.setOnAction(e -> handleInOrderTraversal());

        HBox controls = new HBox(10, input, insert, delete, search, inOrder);
        controls.setPadding(new Insets(10));

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: #BDC3C7; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(900);

        getChildren().addAll(title, controls, canvas);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(120);
        logArea.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 13px;");
        getChildren().add(logArea);
    }

    private void logStep(String message) {
        logArea.appendText(message + "\n");
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

            List<BinaryTreeNode> path = tree.getSearchPath(value);
            BinaryTreeNode insertParent = path.isEmpty() ? null : path.get(path.size() - 1);

            SequentialTransition animation = new SequentialTransition();
            logStep("Iniciando inserción del valor " + value);

            for (BinaryTreeNode node : path) {
                TreeNodeView nodeView = new TreeNodeView(node.value);
                nodeView.setTranslateX(node.x);
                nodeView.setTranslateY(node.y);
                canvas.getChildren().add(nodeView);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    nodeView.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
                    logStep("Visitando nodo con valor " + node.value);
                });
                animation.getChildren().add(pause);
            }

            // Agregar una pausa final antes de insertar
            animation.setOnFinished(e -> {
                if (!path.isEmpty()) {
                    logStep("Posición de inserción determinada después del nodo: " + insertParent.value);

                    PauseTransition pauseBeforeInsert = new PauseTransition(Duration.seconds(1));
                    pauseBeforeInsert.setOnFinished(ev -> {
                        tree.insert(value);
                        drawTree();
                        logStep("Valor insertado: " + value);
                    });
                    pauseBeforeInsert.play();
                } else {
                    // Árbol vacío, insertamos directamente con pausa
                    PauseTransition pauseInsertRoot = new PauseTransition(Duration.seconds(1));
                    pauseInsertRoot.setOnFinished(ev -> {
                        tree.insert(value);
                        drawTree();
                        logStep("Valor insertado como raíz: " + value);
                    });
                    pauseInsertRoot.play();
                }
            });

            animation.play();
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            logArea.clear();

            List<BinaryTreeNode> path = tree.getSearchPath(value);
            BinaryTreeNode foundNode = path.isEmpty() || path.get(path.size() - 1).value != value ? null : path.get(path.size() - 1);

            SequentialTransition animation = new SequentialTransition();
            logStep("Iniciando búsqueda para eliminación del valor " + value);

            for (BinaryTreeNode node : path) {
                TreeNodeView nodeView = new TreeNodeView(node.value);
                nodeView.setTranslateX(node.x);
                nodeView.setTranslateY(node.y);
                canvas.getChildren().add(nodeView);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    if (node == foundNode) {
                        nodeView.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-width: 3px;");
                        logStep("Nodo a eliminar encontrado: " + node.value);
                    } else {
                        nodeView.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
                        logStep("Visitando nodo con valor " + node.value);
                    }
                });
                animation.getChildren().add(pause);
            }

            animation.setOnFinished(e -> {
                if (foundNode == null) {
                    logStep("Valor no encontrado. No se puede eliminar.");
                } else {
                    PauseTransition pauseBeforeDelete = new PauseTransition(Duration.seconds(1));
                    pauseBeforeDelete.setOnFinished(ev -> {
                        logStep("Eliminando nodo: " + value);
                        tree.delete(value);
                        drawTree();
                        logStep("Nodo eliminado y árbol actualizado.");
                    });
                    pauseBeforeDelete.play();
                }
            });

            animation.play();

        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }


    private void handleSearch() {
        try {
            int value = Integer.parseInt(input.getText());
            input.clear();
            drawTree();
            logArea.clear();

            List<BinaryTreeNode> path = tree.getSearchPath(value);
            BinaryTreeNode foundNode = path.isEmpty() || path.get(path.size() - 1).value != value ? null : path.get(path.size() - 1);

            animateSearchPath(path, foundNode, value);
        } catch (NumberFormatException ex) {
            showError("Ingrese un número válido");
        }
    }

    private void handleInOrderTraversal() {
        List<BinaryTreeNode> traversal = tree.inOrderTraversal();

        logArea.clear();
        drawTree();
        logStep("Iniciando recorrido InOrden...");

        SequentialTransition animation = new SequentialTransition();

        for (BinaryTreeNode node : traversal) {
            TreeNodeView nodeView = new TreeNodeView(node.value);
            nodeView.setTranslateX(node.x);
            nodeView.setTranslateY(node.y);
            canvas.getChildren().add(nodeView);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                nodeView.setStyle("-fx-background-color: lightgreen; -fx-border-color: green; -fx-border-width: 2px;");
                logStep("Visitando nodo: " + node.value);
            });

            animation.getChildren().add(pause);
        }

        animation.setOnFinished(e -> logStep("Recorrido InOrden finalizado."));
        animation.play();
    }


    private void animateSearchPath(List<BinaryTreeNode> path, BinaryTreeNode foundNode, int target) {
        SequentialTransition animation = new SequentialTransition();
        logStep("Iniciando búsqueda del valor " + target);

        for (BinaryTreeNode node : path) {
            TreeNodeView nodeView = new TreeNodeView(node.value);
            nodeView.setTranslateX(node.x);
            nodeView.setTranslateY(node.y);
            canvas.getChildren().add(nodeView);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                if (node == foundNode) {
                    nodeView.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-border-width: 3px;");
                    logStep("Nodo encontrado: " + node.value);
                } else {
                    nodeView.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
                    logStep("Visitando nodo con valor " + node.value);
                }
            });
            animation.getChildren().add(pause);
        }

        animation.setOnFinished(e -> {
            if (foundNode == null) {
                logStep("Valor no encontrado.");
            } else {
                logStep("Valor encontrado: " + target);
            }
        });

        animation.play();
    }

    private void drawTree() {
        canvas.getChildren().clear();
        drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void drawNode(BinaryTreeNode node, double x, double y, double offset) {
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

    private void drawNodeWithHighlight(BinaryTreeNode node, double x, double y, double offset, int target) {
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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Entrada inválida");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
