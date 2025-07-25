package com.eda.backend.serializers;

import com.eda.backend.structures.BinaryTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinaryTreeSerializer implements EstructuraSerializer<BinaryTree> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String serializar(BinaryTree estructura) {
        try {
            return objectMapper.writeValueAsString(estructura);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando BinaryTree", e);
        }
    }

    @Override
    public BinaryTree deserializar(String json) {
        try {
            return objectMapper.readValue(json, BinaryTree.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializando BinaryTree", e);
        }
    }
}
