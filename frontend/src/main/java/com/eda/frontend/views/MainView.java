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

        Label title = new Label("Estructuras");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        Button btnBTree = new Button("Árbol B");
        Button btnStack = new Button("Pila");
        Button btnQueue = new Button("Cola");
        Button btnList = new Button("Lista");

        btnBTree.setMaxWidth(Double.MAX_VALUE);
        btnStack.setMaxWidth(Double.MAX_VALUE);
        btnQueue.setMaxWidth(Double.MAX_VALUE);
        btnList.setMaxWidth(Double.MAX_VALUE);

        btnBTree.setOnAction(e -> content.getChildren().setAll(new BTreeView()));
        btnStack.setOnAction(e -> content.getChildren().setAll(new StackView()));
        btnQueue.setOnAction(e -> content.getChildren().setAll(new QueueView()));
        btnList.setOnAction(e -> content.getChildren().setAll(new ListViewPanel()));

        menuBox.getChildren().addAll(title, btnBTree, btnStack, btnQueue, btnList);
        return menuBox;
    }
}
