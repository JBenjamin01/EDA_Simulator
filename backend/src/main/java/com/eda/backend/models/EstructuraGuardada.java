package com.eda.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estructuras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstructuraGuardada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String tipo; // ej. "BINARY", "SPLAY", "AVL", "B"

    @Lob
    @Column(columnDefinition = "TEXT")
    private String datosJson; // La estructura serializada (lista, árbol, etc.)
}
