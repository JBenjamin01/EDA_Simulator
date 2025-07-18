package com.eda.frontend.components;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TreeNodeView extends StackPane {

    public TreeNodeView(int value) {
        Circle circle = new Circle(20);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.DARKBLUE);
        Text text = new Text(String.valueOf(value));

        getChildren().addAll(circle, text);
        setTranslateX(0);
        setTranslateY(0);
    }
}
