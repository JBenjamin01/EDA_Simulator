package com.eda.backend.serializers;

public interface EstructuraSerializer<T> {
    String serializar(T estructura);
    T deserializar(String json);
}
