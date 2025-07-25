package com.eda.backend.structures;

public class BinaryTree {
    private BinaryNode raiz;

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private BinaryNode insertarRecursivo(BinaryNode nodo, int valor) {
        if (nodo == null) return new BinaryNode(valor, null, null);

        if (valor < nodo.getValor()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), valor));
        } else {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), valor));
        }
        return nodo;
    }

    public BinaryNode getRaiz() {
        return raiz;
    }
}