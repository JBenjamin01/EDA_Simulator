package com.eda.backend.controllers;

import com.eda.backend.dto.EstructuraDTO;
import com.eda.backend.models.EstructuraGuardada;
import com.eda.backend.repositories.EstructuraGuardadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estructuras")
@RequiredArgsConstructor
@CrossOrigin
public class EstructuraController {

    private final EstructuraGuardadaRepository repository;

    @PostMapping("/guardar")
    public EstructuraGuardada guardar(@RequestBody EstructuraDTO dto) {
        return repository.save(EstructuraGuardada.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .datosJson(dto.getDatosJson())
                .build());
    }

    @GetMapping("/listar/{tipo}")
    public List<EstructuraGuardada> listarPorTipo(@PathVariable String tipo) {
        return repository.findByTipo(tipo.toUpperCase());
    }

    @GetMapping("/{id}")
    public EstructuraGuardada obtenerPorId(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }
}
