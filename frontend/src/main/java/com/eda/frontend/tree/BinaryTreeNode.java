package com.eda.frontend.tree;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    public double x, y; // coordenadas para el dibujo

    public BinaryTreeNode() {
        // Constructor vacío requerido por Jackson
    }

    public BinaryTreeNode(int value) {
        this.value = value;
    }
}
