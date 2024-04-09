package com.food.recipes.controller;

import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import com.food.recipes.service.RecipesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:3000" })
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

    @GetMapping("/filter")
    public ResponseEntity<List<RecipesResponseDTO>> findByName(@RequestParam("name") String name) {
        List<RecipesResponseDTO> response = service.findByName(name);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipesResponseDTO> findById(@PathVariable String id) {
        RecipesResponseDTO response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<RecipesResponseDTO> save(@RequestBody @Valid RecipesRequestDTO request) {
        RecipesResponseDTO response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/title/{id}")
    public ResponseEntity<RecipesResponseDTO> updateTitle(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateTitle(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/ingredients/{id}")
    public ResponseEntity<RecipesResponseDTO> updateIngredients(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateIngredients(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/method/{id}")
    public ResponseEntity<RecipesResponseDTO> updateMethodPreparation(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateMethodPreparation(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<?> disableRecipe(@PathVariable String id) {
        service.disableRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
