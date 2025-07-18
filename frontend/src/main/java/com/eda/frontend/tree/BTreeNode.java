package com.eda.frontend.tree;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    public List<Integer> keys;
    public List<BTreeNode> children;
    public boolean leaf;

    public BTreeNode(boolean leaf) {
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}
