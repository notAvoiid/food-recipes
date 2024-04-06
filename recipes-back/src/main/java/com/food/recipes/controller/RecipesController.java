package com.food.recipes.controller;

import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import com.food.recipes.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipesController {

    private final RecipesService service;

    public RecipesController(RecipesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecipesResponseDTO>> findAll() {
        List<RecipesResponseDTO> response = service.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipesResponseDTO> findById(@PathVariable UUID id) {
        RecipesResponseDTO response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<RecipesResponseDTO> save(@RequestBody RecipesRequestDTO request) {
        RecipesResponseDTO response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
