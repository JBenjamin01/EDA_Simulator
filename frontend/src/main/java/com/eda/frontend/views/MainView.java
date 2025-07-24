package com.eda.frontend.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MainView extends BorderPane {

    private final VBox menu;
    private final StackPane content;
    private Button selectedButton = null;

    public MainView() {
        menu = buildMenu();
        content = buildWelcomeScreen();

        setLeft(menu);
        setCenter(content);
    }

    private VBox buildMenu() {
        VBox menuBox = new VBox();
        menuBox.setSpacing(20);
        menuBox.setPadding(new Insets(30, 20, 30, 20));
        menuBox.setStyle("-fx-background-color: #2C3E50;");
        menuBox.setPrefWidth(260);

        Label title = new Label("Estructuras de Datos");
        title.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 20px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);

        title.setOnMouseClicked(e -> {
            updateSelectedButton(null);
            content.getChildren().setAll(buildWelcomeScreen());
        });

        Separator separator = new Separator();

        Button btnBinaryTree = new Button("Árbol Binario");
        Button btnAvlTree = new Button("Árbol AVL");
        Button btnSplayTree = new Button("Árbol Splay");
        Button btnBTree = new Button("Árbol B");

        for (Button btn : new Button[]{btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree}) {
            styleMenuButton(btn);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                updateSelectedButton(btn);
                if (btn == btnBinaryTree) content.getChildren().setAll(new BinaryTreeView());
                else if (btn == btnAvlTree) content.getChildren().setAll(new AVLTreeView());
                else if (btn == btnSplayTree) content.getChildren().setAll(new SplayTreeView());
                else if (btn == btnBTree) content.getChildren().setAll(new BTreeView());
            });
        }

        // Botón "Acerca de" con ícono
        ImageView infoIcon = new ImageView(new Image(getClass().getResource("/icons/info.png").toExternalForm()));
        infoIcon.setFitWidth(16);
        infoIcon.setFitHeight(16);

        Button btnAbout = new Button("Acerca de", infoIcon);
        btnAbout.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #95A5A6;
            -fx-font-size: 15px;
            -fx-underline: true;
            -fx-cursor: hand;
        """);
        btnAbout.setOnMouseEntered(e -> btnAbout.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-underline: true;
            -fx-cursor: hand;
        """));
        btnAbout.setOnMouseExited(e -> btnAbout.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #95A5A6;
            -fx-font-size: 15px;
            -fx-underline: true;
            -fx-cursor: hand;
        """));
        btnAbout.setOnAction(e -> {
            updateSelectedButton(null);
            content.getChildren().setAll(new AboutView());
        });

        VBox topButtons = new VBox(10, title, separator, btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox bottomButtons = new VBox();
        bottomButtons.setSpacing(15);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(10, 0, 0, 0));
        bottomButtons.getChildren().addAll(btnAbout);  // Botón salir eliminado

        menuBox.getChildren().addAll(topButtons, spacer, bottomButtons);
        return menuBox;
    }

    private void updateSelectedButton(Button newSelected) {
        if (selectedButton != null) {
            styleMenuButton(selectedButton);
        }
        selectedButton = newSelected;
        if (selectedButton != null) {
            selectedButton.setStyle("""
                -fx-background-color: #1ABC9C;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-cursor: hand;
            """);
        }
    }

    private StackPane buildWelcomeScreen() {
        VBox welcomeBox = new VBox(15);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(40));

        Label title = new Label("Simulador de Estructuras de Datos");
        title.setFont(Font.font("Arial", 28));
        title.setStyle("-fx-text-fill: #2C3E50;");

        Label subtitle = new Label("¡Explora, aprende e interactúa con estructuras como árboles y listas!");
        subtitle.setStyle("-fx-text-fill: #555555; -fx-font-size: 16px;");

        Label hint = new Label("Selecciona una estructura desde el menú izquierdo para comenzar.");
        hint.setStyle("-fx-text-fill: #777777; -fx-font-size: 14px;");

        ImageView icon = new ImageView(new Image(getClass().getResource("/icons/eda.jpg").toExternalForm()));
        icon.setFitWidth(120);
        icon.setFitHeight(120);

        welcomeBox.getChildren().addAll(icon, title, subtitle, hint);

        StackPane pane = new StackPane(welcomeBox);
        pane.setStyle("-fx-background-color: #ECF0F1;");
        return pane;
    }

    private void styleMenuButton(Button btn) {
        btn.setStyle("""
            -fx-background-color: #3498DB;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-cursor: hand;
        """);
        btn.setOnMouseEntered(e -> btn.setStyle("""
            -fx-background-color: #2980B9;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-cursor: hand;
        """));
        btn.setOnMouseExited(e -> {
            if (btn != selectedButton) {
                btn.setStyle("""
                    -fx-background-color: #3498DB;
                    -fx-text-fill: white;
                    -fx-font-size: 14px;
                    -fx-cursor: hand;
                """);
            }
        });
    }
}