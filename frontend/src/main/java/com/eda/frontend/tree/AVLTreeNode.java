package com.eda.frontend.tree;

import java.io.Serializable;

public class AVLTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    public int value;
    public AVLTreeNode left, right;
    public int height;
    public double x, y;

    public AVLTreeNode(int value) {
        this.value = value;
        this.height = 1;
    }
}
