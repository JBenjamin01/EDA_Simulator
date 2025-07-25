package com.eda.backend.services;

import com.eda.backend.serializers.*;
import com.eda.backend.structures.BinaryTree;
import org.springframework.stereotype.Service;

@Service
public class EstructuraService {

    private final BinaryTreeSerializer binarySerializer = new BinaryTreeSerializer();

    public String guardarBinaryTree(BinaryTree arbol) {
        return binarySerializer.serializar(arbol);
    }

    public BinaryTree cargarBinaryTree(String json) {
        return binarySerializer.deserializar(json);
    }
}