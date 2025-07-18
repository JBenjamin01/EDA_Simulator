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

    public void delete(int value) {
        root = splay(root, value);

        if (root == null || root.value != value) return;

        if (root.left == null) {
            root = root.right;
        } else {
            SplayTreeNode leftSubtree = root.left;
            SplayTreeNode rightSubtree = root.right;
            leftSubtree = splay(leftSubtree, value); // Traer el mayor de la izquierda a la raíz
            leftSubtree.right = rightSubtree;
            root = leftSubtree;
        }
    }

    public SplayTreeNode search(int value) {
        root = searchAndSplay(root, value);
        if (root != null && root.value == value) {
            return root;
        }
        return null;
    }

    private SplayTreeNode searchAndSplay(SplayTreeNode node, int value) {
        if (node == null || node.value == value) return splay(node, value);

        if (value < node.value) {
            if (node.left == null) return splay(node, value);
            node.left = searchAndSplay(node.left, value);
        } else {
            if (node.right == null) return splay(node, value);
            node.right = searchAndSplay(node.right, value);
        }
        return splay(node, value);
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

        if (value < root.value) {
            if (root.left == null) return root;

            if (value < root.left.value) {
                root.left.left = splay(root.left.left, value);
                root = rotateRight(root);
            } else if (value > root.left.value) {
                root.left.right = splay(root.left.right, value);
                if (root.left.right != null)
                    root.left = rotateLeft(root.left);
            }

            return (root.left == null) ? root : rotateRight(root);
        } else {
            if (root.right == null) return root;

            if (value > root.right.value) {
                root.right.right = splay(root.right.right, value);
                root = rotateLeft(root);
            } else if (value < root.right.value) {
                root.right.left = splay(root.right.left, value);
                if (root.right.left != null)
                    root.right = rotateRight(root.right);
            }

            return (root.right == null) ? root : rotateLeft(root);
        }
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
