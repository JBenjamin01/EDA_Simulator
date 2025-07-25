package com.eda.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstructuraDTO {
    private String nombre;
    private String tipo;
    private String datosJson;
}
