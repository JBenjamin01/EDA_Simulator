package com.eda.backend.controller;

import com.eda.backend.model.BTree;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model/bTree")
public class BTreeController {

    private BTree bTree = new BTree(3); // inicialización simple

    @PostMapping("/insert")
    @Operation(summary = "Insertar un valor en el Árbol B")
    public Map<String, Object> insert(@RequestParam int value) {
        bTree.insert(value);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Valor insertado.");
        response.put("traversal", bTree.toArray());
        return response;
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

    @GetMapping("/traversal")
    public List<Integer> traversal() {
        return bTree.toArray();
    }

    @PostMapping("/reset")
    public Map<String, String> reset() {
        bTree.reset();
        return Map.of("message", "Árbol reiniciado.");
    }

}
