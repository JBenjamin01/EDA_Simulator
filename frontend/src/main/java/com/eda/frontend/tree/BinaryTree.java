package com.eda.frontend.tree;

public class BinaryTree {

    private BinaryTreeNode root;

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode current, int value) {
        if (current == null) {
            return new BinaryTreeNode(value);
        }

        if (value < current.value) {
            current.left = insertRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = insertRecursive(current.right, value);
        }

        return current;
    }
}
