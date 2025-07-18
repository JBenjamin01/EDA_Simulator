package com.eda.backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BTree {
    private BTreeNode root;
    private int degree;

    public BTree() {
        this.degree = 3; // default
    }

    public BTree(int degree) {
        this.degree = degree;
        this.root = null;
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public List<Integer> toArray() {
        List<Integer> result = new ArrayList<>();
        collectKeys(this.root, result);
        Collections.sort(result);
        return result;
    }

    private void collectKeys(BTreeNode node, List<Integer> result) {
        if (node == null) return;

        result.addAll(node.getKeys());

        if (!node.isLeaf()) {
            for (BTreeNode child : node.getChildren()) {
                collectKeys(child, result);
            }
        }
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(degree, true);
            root.getKeys().add(key);
        } else {
            if (root.getKeys().size() == 2 * degree - 1) {
                BTreeNode newRoot = new BTreeNode(degree, false);
                newRoot.getChildren().add(root);
                splitChild(newRoot, 0);
                insertNonFull(newRoot, key);
                root = newRoot;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.getKeys().size() - 1;

        if (node.isLeaf()) {
            node.getKeys().add(0); // Espacio
            while (i >= 0 && key < node.getKeys().get(i)) {
                node.getKeys().set(i + 1, node.getKeys().get(i));
                i--;
            }
            node.getKeys().set(i + 1, key);
        } else {
            while (i >= 0 && key < node.getKeys().get(i)) {
                i--;
            }
            i++;
            BTreeNode child = node.getChildren().get(i);
            if (child.getKeys().size() == 2 * degree - 1) {
                splitChild(node, i);
                if (key > node.getKeys().get(i)) {
                    i++;
                }
            }
            insertNonFull(node.getChildren().get(i), key);
        }
    }

    private void splitChild(BTreeNode parent, int index) {
        BTreeNode fullChild = parent.getChildren().get(index);
        BTreeNode newChild = new BTreeNode(degree, fullChild.isLeaf());

        // Mover mitad derecha de claves
        for (int j = 0; j < degree - 1; j++) {
            newChild.getKeys().add(fullChild.getKeys().remove(degree));
        }

        // Mover hijos si no es hoja
        if (!fullChild.isLeaf()) {
            for (int j = 0; j < degree; j++) {
                newChild.getChildren().add(fullChild.getChildren().remove(degree));
            }
        }

        parent.getChildren().add(index + 1, newChild);
        parent.getKeys().add(index, fullChild.getKeys().remove(degree - 1));
    }

    public void reset() {
    this.root = null;
}




}
