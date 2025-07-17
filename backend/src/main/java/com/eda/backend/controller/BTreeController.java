package com.eda.backend.controller;


import com.eda.backend.model.BTree;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/btree")
public class BTreeController {

    private BTree bTree = new BTree(3); // inicialización simple

    @PostMapping("/insert")
    @Operation(summary = "Insertar un valor en el Árbol B")
    public String insert(@RequestParam int value) {
        // Lógica de inserción aquí
        // Solo placeholder por ahora
        return "Insertado " + value;
    }

    @PostMapping("/delete")
    @Operation(summary = "Eliminar un valor del Árbol B")
    public String delete(@RequestParam int value) {
        return "Eliminado " + value;
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar un valor en el Árbol B")
    public String search(@RequestParam int value) {
        return "Buscado " + value;
    }

    @GetMapping("/display")
    @Operation(summary = "Mostrar estructura del Árbol B")
    public BTree getTree() {
        return bTree;
    }
}
