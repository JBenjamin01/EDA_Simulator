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

        // ▸ Estructuras Jerárquicas
        Label treeTitle = new Label("Estructuras Jerárquicas ▾");
        treeTitle.setStyle("""
            -fx-text-fill: #ECF0F1;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """);
        treeTitle.setPadding(new Insets(5, 0, 0, 10));

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

        VBox treeSection = new VBox(8, btnBinaryTree, btnAvlTree, btnSplayTree, btnBTree);
        treeSection.setPadding(new Insets(5, 0, 0, 20));
        treeSection.setVisible(true);
        treeSection.setManaged(true);

        treeTitle.setOnMouseClicked(e -> {
            boolean visible = treeSection.isVisible();
            treeSection.setVisible(!visible);
            treeSection.setManaged(!visible);
            treeTitle.setText("Estructuras Jerárquicas " + (visible ? "▸" : "▾"));
        });

        // ▸ Estructuras No Jerárquicas
        Label listTitle = new Label("Estructuras No Jerárquicas ▾");
        listTitle.setStyle("""
            -fx-text-fill: #ECF0F1;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """);
        listTitle.setPadding(new Insets(10, 0, 0, 10));

        Button btnListStackQueue = new Button("Listas, Pilas y Colas");
        styleMenuButton(btnListStackQueue);
        btnListStackQueue.setMaxWidth(Double.MAX_VALUE);
        btnListStackQueue.setOnAction(e -> {
            updateSelectedButton(btnListStackQueue);
            content.getChildren().setAll(); // <-- Asegúrate de tener esta vista creada
        });

        VBox nonTreeSection = new VBox(8, btnListStackQueue);
        nonTreeSection.setPadding(new Insets(5, 0, 0, 20));
        nonTreeSection.setVisible(true);
        nonTreeSection.setManaged(true);

        listTitle.setOnMouseClicked(e -> {
            boolean visible = nonTreeSection.isVisible();
            nonTreeSection.setVisible(!visible);
            nonTreeSection.setManaged(!visible);
            listTitle.setText("Estructuras No Jerárquicas " + (visible ? "▸" : "▾"));
        });

        // Botón "Acerca de"
        ImageView infoIcon = new ImageView(new Image(getClass().getResource("/icons/info.png").toExternalForm()));
        infoIcon.setFitWidth(16);
        infoIcon.setFitHeight(16);

        Button btnAbout = new Button("Acerca de", infoIcon);
        btnAbout.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #95A5A6;
            -fx-font-size: 16px;
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

        VBox topButtons = new VBox(10, title, separator, treeTitle, treeSection, listTitle, nonTreeSection);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox bottomButtons = new VBox();
        bottomButtons.setSpacing(15);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(10, 0, 0, 0));
        bottomButtons.getChildren().addAll(btnAbout);

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
        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(60));

        // Logo actualizado
        ImageView logo = new ImageView(new Image(getClass().getResource("/icons/eda.png").toExternalForm()));
        logo.setFitWidth(540);
        logo.setPreserveRatio(true);

        // Separación visual entre logo y texto
        Region spacer = new Region();
        spacer.setMinHeight(15);

        // Título principal
        Label title = new Label("¡Bienvenido a EDA Simulator!");
        title.setFont(Font.font("Arial", 40));
        title.setStyle("-fx-text-fill: #2C3E50; -fx-font-weight: bold;");

        // Descripción clara y centrada
        Label description = new Label("""
            Una herramienta visual e interactiva diseñada para facilitar la comprensión 
            y simulación de estructuras de datos complejas como árboles binarios, AVL, 
            Splay y B. Ideal para estudiantes y entusiastas de la informática.
        """);
        description.setWrapText(true);
        description.setMaxWidth(700);
        description.setStyle("-fx-text-fill: #555555; -fx-font-size: 18px; -fx-alignment: center;");

        // Instrucción final
        Label hint = new Label("Usa el menú de la izquierda para comenzar con una estructura.");
        hint.setStyle("-fx-text-fill: #777777; -fx-font-size: 16px;");

        welcomeBox.getChildren().addAll(logo, spacer, title, description, hint);

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