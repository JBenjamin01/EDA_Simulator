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
}
