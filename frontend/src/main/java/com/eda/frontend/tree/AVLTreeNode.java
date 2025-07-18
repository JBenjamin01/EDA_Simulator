package com.eda.frontend.tree;

public class AVLTreeNode {
    public int value;
    public AVLTreeNode left, right;
    public int height;
    public double x, y;

    public AVLTreeNode(int value) {
        this.value = value;
        this.height = 1;
    }
}
