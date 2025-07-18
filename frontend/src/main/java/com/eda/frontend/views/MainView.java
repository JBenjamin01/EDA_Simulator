package com.eda.frontend.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainView extends BorderPane {

    private final VBox menu;
    private final StackPane content;

    public MainView() {
        menu = buildMenu();
        content = buildWelcomeScreen();

        setLeft(menu);
        setCenter(content);
    }

    private VBox buildMenu() {
        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(20));
        menuBox.setStyle("-fx-background-color: #2C3E50;");
        menuBox.setPrefWidth(260); // Aumentado el ancho

        Label title = new Label("Estructuras de Datos");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnBinaryTree = new Button("Árbol Binario");
        Button btnAvlTree = new Button("Árbol AVL");
        Button btnSplayTree = new Button("Árbol Splay");
        Button btnBTree = new Button("Árbol B");

        for (Button btn : new Button[]{btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("""
                -fx-background-color: #3498DB;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-cursor: hand;
            """);

            // Efecto hover visual
            btn.setOnMouseEntered(e -> btn.setStyle("""
                -fx-background-color: #2980B9;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-cursor: hand;
            """));
            btn.setOnMouseExited(e -> btn.setStyle("""
                -fx-background-color: #3498DB;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-cursor: hand;
            """));
        }

        btnBinaryTree.setOnAction(e -> content.getChildren().setAll(new BinaryTreeView()));
        btnAvlTree.setOnAction(e -> content.getChildren().setAll(new AVLTreeView()));
        btnSplayTree.setOnAction(e -> content.getChildren().setAll(new SplayTreeView()));
        btnBTree.setOnAction(e -> content.getChildren().setAll(new BTreeView()));

        menuBox.getChildren().addAll(title, btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree);
        return menuBox;
    }


    private StackPane buildWelcomeScreen() {
        VBox welcomeBox = new VBox(15);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(40));

        Label title = new Label("Simulador de Estructuras de Datos");
        title.setFont(Font.font("Arial", 28));
        title.setStyle("-fx-text-fill: #2C3E50;");

        Label subtitle = new Label("¡Explora, aprende e interactúa con estructuras de datos!");
        subtitle.setStyle("-fx-text-fill: #555555; -fx-font-size: 16px;");

        // Imagen opcional (asegúrate de tenerla en /resources/icons/)
        ImageView icon = new ImageView(new Image(getClass().getResource("/icons/eda.jpg").toExternalForm()));
        icon.setFitWidth(120);
        icon.setFitHeight(120);

        welcomeBox.getChildren().addAll(icon, title, subtitle);

        StackPane pane = new StackPane(welcomeBox);
        pane.setStyle("-fx-background-color: #ECF0F1;");
        return pane;
    }
}
