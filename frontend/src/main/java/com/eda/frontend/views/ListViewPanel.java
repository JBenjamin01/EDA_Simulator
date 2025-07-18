package com.eda.frontend.views;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ListViewPanel extends StackPane {

    public ListViewPanel() {
        Text label = new Text("Vista de la Lista");
        getChildren().add(label);
    }
}
