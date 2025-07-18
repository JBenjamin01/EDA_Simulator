package com.eda.frontend.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class MainView extends BorderPane {

    private final VBox menu;
    private final StackPane content;

    public MainView() {
        menu = buildMenu();
        content = new StackPane();
        content.setPadding(new Insets(10));
        content.getChildren().add(new Text("Bienvenido al Simulador de EDA"));

        setLeft(menu);
        setCenter(content);
    }

    private VBox buildMenu() {
        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(10));
        menuBox.setStyle("-fx-background-color: #2C3E50;");
        menuBox.setPrefWidth(200);

        Label title = new Label("Estructuras de Datos");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        Button btnBinaryTree = new Button("Árbol Binario");
        Button btnAvlTree = new Button("Árbol AVL");
        Button btnSplayTree = new Button("Árbol Splay");
        Button btnBTree = new Button("Árbol B");

        btnBinaryTree.setMaxWidth(Double.MAX_VALUE);
        btnAvlTree.setMaxWidth(Double.MAX_VALUE);
        btnSplayTree.setMaxWidth(Double.MAX_VALUE);
        btnBTree.setMaxWidth(Double.MAX_VALUE);

        btnBinaryTree.setOnAction(e -> content.getChildren().setAll(new BinaryTreeView()));
        btnAvlTree.setOnAction(e -> content.getChildren().setAll(new AVLTreeView()));
        btnSplayTree.setOnAction(e -> content.getChildren().setAll(new SplayTreeView()));
        btnBTree.setOnAction(e -> content.getChildren().setAll(new BTreeView()));

        menuBox.getChildren().addAll(title, btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree);
        return menuBox;
    }
}
