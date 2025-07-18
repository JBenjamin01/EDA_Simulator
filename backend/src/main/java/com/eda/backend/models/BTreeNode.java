package com.eda.backend.models;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    private int degree;
    private List<Integer> keys;
    private List<BTreeNode> children;
    private boolean isLeaf;

    public BTreeNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = true;
    }

    public BTreeNode(int degree, boolean isLeaf) {
        this.degree = degree;
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // Getters y setters

    public int getDegree() { return degree; }
    public void setDegree(int degree) { this.degree = degree; }

    public List<Integer> getKeys() { return keys; }
    public void setKeys(List<Integer> keys) { this.keys = keys; }

    public List<BTreeNode> getChildren() { return children; }
    public void setChildren(List<BTreeNode> children) { this.children = children; }

    public boolean isLeaf() { return isLeaf; }
    public void setLeaf(boolean leaf) { isLeaf = leaf; }
}