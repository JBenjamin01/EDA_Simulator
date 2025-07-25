package com.eda.backend.structures;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinaryNode {
    private int valor;
    private BinaryNode izquierdo;
    private BinaryNode derecho;
}
