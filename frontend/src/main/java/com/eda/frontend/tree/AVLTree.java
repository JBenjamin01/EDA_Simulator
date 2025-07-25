package com.eda.frontend.tree;

import java.io.*;

/**
 * Implementación del árbol AVL con operaciones básicas:
 * insertar, eliminar, buscar, balancear, guardar y cargar.
 */
public class AVLTree {

    private AVLTreeNode root;

    public AVLTreeNode getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    public void delete(int value) {
        root = delete(root, value);
    }

    public AVLTreeNode search(int value) {
        return search(root, value);
    }

    private AVLTreeNode search(AVLTreeNode node, int value) {
        if (node == null || node.value == value) return node;
        return value < node.value ? search(node.left, value) : search(node.right, value);
    }

    private AVLTreeNode insert(AVLTreeNode node, int value) {
        if (node == null) return new AVLTreeNode(value);

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node; // No duplicates allowed
        }

        updateHeight(node);
        return balance(node);
    }

    private AVLTreeNode delete(AVLTreeNode node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLTreeNode min = findMin(node.right);
                node.value = min.value;
                node.right = delete(node.right, min.value);
            }
        }

        if (node == null) return null;

        updateHeight(node);
        return balance(node);
    }

    private AVLTreeNode findMin(AVLTreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void updateHeight(AVLTreeNode node) {
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        node.height = 1 + Math.max(leftHeight, rightHeight);
    }

    private int getHeight(AVLTreeNode node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLTreeNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private AVLTreeNode balance(AVLTreeNode node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private AVLTreeNode rotateRight(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private AVLTreeNode rotateLeft(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /**
     * Guarda el árbol actual en un archivo serializado.
     */
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(root);
        }
    }

    /**
     * Carga un árbol desde un archivo serializado.
     */
    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            root = (AVLTreeNode) ois.readObject();
        }
    }
}
