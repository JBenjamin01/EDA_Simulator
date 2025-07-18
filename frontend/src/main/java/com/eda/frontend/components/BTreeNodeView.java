package com.eda.frontend.components;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;

import java.util.List;

public class BTreeNodeView extends HBox {

    public BTreeNodeView(List<Integer> keys) {
        setSpacing(2);
        setPadding(new Insets(5));
        setStyle("-fx-border-color: darkblue; -fx-background-color: #D0E6F7;");
        
        for (Integer key : keys) {
            StackPane keyBox = createKeyBox(key);
            getChildren().add(keyBox);
        }
    }

    private StackPane createKeyBox(int key) {
        Rectangle rect = new Rectangle(30, 30);
        rect.setArcWidth(6);
        rect.setArcHeight(6);
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.DARKBLUE);

        Text text = new Text(String.valueOf(key));
        StackPane box = new StackPane(rect, text);
        return box;
    }
}
