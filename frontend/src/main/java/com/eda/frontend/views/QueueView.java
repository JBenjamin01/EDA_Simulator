package com.eda.frontend.views;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class QueueView extends StackPane {

    public QueueView() {
        Text label = new Text("Vista de la Cola");
        getChildren().add(label);
    }
}
