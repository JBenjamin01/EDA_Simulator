package com.eda.frontend.tree;

public class SplayTree {
    private SplayTreeNode root;

    public SplayTreeNode getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insert(root, value);
        root = splay(root, value);
    }

    private SplayTreeNode insert(SplayTreeNode node, int value) {
        if (node == null) return new SplayTreeNode(value);
        if (value < node.value)
            node.left = insert(node.left, value);
        else if (value > node.value)
            node.right = insert(node.right, value);
        return node;
    }

    private SplayTreeNode splay(SplayTreeNode root, int value) {
        if (root == null || root.value == value)
            return root;

        // Zig-Zig (Left Left)
        if (value < root.value && root.left != null && value < root.left.value) {
            root.left.left = splay(root.left.left, value);
            root = rotateRight(root);
        }

        // Zig-Zag (Left Right)
        else if (value < root.value && root.left != null && value > root.left.value) {
            root.left.right = splay(root.left.right, value);
            if (root.left.right != null)
                root.left = rotateLeft(root.left);
        }

        // Zag-Zig (Right Left)
        else if (value > root.value && root.right != null && value < root.right.value) {
            root.right.left = splay(root.right.left, value);
            if (root.right.left != null)
                root.right = rotateRight(root.right);
        }

        // Zag-Zag (Right Right)
        else if (value > root.value && root.right != null && value > root.right.value) {
            root.right.right = splay(root.right.right, value);
            root = rotateLeft(root);
        }

        return (value < root.value && root.left != null) ? rotateRight(root)
             : (value > root.value && root.right != null) ? rotateLeft(root)
             : root;
    }

    private SplayTreeNode rotateRight(SplayTreeNode x) {
        SplayTreeNode y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    private SplayTreeNode rotateLeft(SplayTreeNode x) {
        SplayTreeNode y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }
}
