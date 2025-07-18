package com.eda.frontend.views;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class StackView extends StackPane {

    public StackView() {
        Text label = new Text("Vista de la Pila");
        getChildren().add(label);
    }
}
