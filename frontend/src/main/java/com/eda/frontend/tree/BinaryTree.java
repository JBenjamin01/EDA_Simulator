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

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int value) {
        if (current == null) {
            return null;
        }

        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = deleteRecursive(current.right, value);
        } else {
            // Nodo encontrado
            if (current.left == null && current.right == null) {
                return null; // sin hijos
            }
            if (current.left == null) {
                return current.right; // un hijo derecho
            }
            if (current.right == null) {
                return current.left; // un hijo izquierdo
            }
            // dos hijos
            int smallestValue = findMin(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
        }

        return current;
    }

    private int findMin(BinaryTreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }
}