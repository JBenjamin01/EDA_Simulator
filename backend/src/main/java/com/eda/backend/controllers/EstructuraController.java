package com.eda.backend.controllers;

import com.eda.backend.dto.EstructuraDTO;
import com.eda.backend.models.EstructuraGuardada;
import com.eda.backend.repositories.EstructuraGuardadaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estructuras")
@RequiredArgsConstructor
@CrossOrigin
public class EstructuraController {

    private final EstructuraGuardadaRepository repository;

    @Operation(summary = "Guardar estructura", description = "Guarda una nueva estructura con su nombre, tipo y datos en formato JSON.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estructura guardada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/guardar")
    public EstructuraGuardada guardar(@RequestBody EstructuraDTO dto) {
        return repository.save(EstructuraGuardada.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .datosJson(dto.getDatosJson())
                .build());
    }

    @Operation(summary = "Listar estructuras por tipo", description = "Obtiene una lista de estructuras guardadas filtradas por tipo (por ejemplo: SPLAY, AVL, etc.).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron estructuras del tipo especificado")
    })
    @GetMapping("/listar/{tipo}")
    public List<EstructuraGuardada> listarPorTipo(@PathVariable String tipo) {
        return repository.findByTipo(tipo.toUpperCase());
    }

    @Operation(summary = "Obtener estructura por ID", description = "Busca una estructura guardada usando su ID único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estructura encontrada"),
        @ApiResponse(responseCode = "404", description = "No se encontró la estructura con ese ID")
    })
    @GetMapping("/{id}")
    public EstructuraGuardada obtenerPorId(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }
}
