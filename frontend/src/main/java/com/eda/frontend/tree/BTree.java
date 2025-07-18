package com.eda.frontend.tree;

public class BTree {
    private final int t; // Grado mínimo
    private BTreeNode root;

    public BTree(int t) {
        this.t = t;
        root = new BTreeNode(true);
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void insert(int key) {
        BTreeNode r = root;
        if (r.keys.size() == 2 * t - 1) {
            BTreeNode s = new BTreeNode(false);
            s.children.add(r);
            splitChild(s, 0, r);
            root = s;
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.keys.size() - 1;

        if (node.leaf) {
            node.keys.add(0); // espacio temporal
            while (i >= 0 && key < node.keys.get(i)) {
                node.keys.set(i + 1, node.keys.get(i));
                i--;
            }
            node.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < node.keys.get(i)) {
                i--;
            }
            i++;
            BTreeNode child = node.children.get(i);
            if (child.keys.size() == 2 * t - 1) {
                splitChild(node, i, child);
                if (key > node.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), key);
        }
    }

    private void splitChild(BTreeNode parent, int index, BTreeNode fullNode) {
        BTreeNode newNode = new BTreeNode(fullNode.leaf);
        for (int j = 0; j < t - 1; j++) {
            newNode.keys.add(fullNode.keys.remove(t));
        }

        if (!fullNode.leaf) {
            for (int j = 0; j < t; j++) {
                newNode.children.add(fullNode.children.remove(t));
            }
        }

        parent.children.add(index + 1, newNode);
        parent.keys.add(index, fullNode.keys.remove(t - 1));
    }
}
