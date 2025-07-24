package com.eda.frontend.tree;

import java.util.ArrayList;
import java.util.List;

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

    public BinaryTreeNode search(int value) {
        return searchRecursive(root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, int value) {
        if (node == null || node.value == value) return node;
        if (value < node.value) return searchRecursive(node.left, value);
        return searchRecursive(node.right, value);
    }

    public List<BinaryTreeNode> getSearchPath(int value) {
        List<BinaryTreeNode> path = new ArrayList<>();
        BinaryTreeNode current = root;

        while (current != null) {
            path.add(current);
            if (value == current.value) break;
            else if (value < current.value) current = current.left;
            else current = current.right;
        }

        return path;
    }

    public List<BinaryTreeNode> inOrderTraversal() {
        List<BinaryTreeNode> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    private void inOrderHelper(BinaryTreeNode node, List<BinaryTreeNode> result) {
        if (node != null) {
            inOrderHelper(node.left, result);
            result.add(node);
            inOrderHelper(node.right, result);
        }
    }
}