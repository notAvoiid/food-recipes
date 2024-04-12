package com.food.recipes.controller;

import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import com.food.recipes.service.RecipesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:3000" })
@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recipes", description = "Endpoint for managing recipes")
public class RecipesController {

    private final RecipesService service;

    public RecipesController(RecipesService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Finds all recipes!", description = "Finds all recipes!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RecipesResponseDTO.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<RecipesResponseDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? DESC : ASC;
        Pageable pageable = of(page, size, by(sortDirection, "title"));
        return ResponseEntity.ok(service.findAll(pageable));
    }


    @GetMapping("/filter")
    @Operation(summary = "Finds all recipes by title!", description = "Finds all recipes by title!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RecipesResponseDTO.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<RecipesResponseDTO>>> findByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? DESC : ASC;
        Pageable pageable = of(page, size, by(sortDirection, "title"));
        return ResponseEntity.ok(service.findByTitle(title, pageable));

    }

    @GetMapping("{id}")
    @Operation(operationId = "id", summary = "Finds a recipe!", description = "Finds a recipe by passing his ID!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<RecipesResponseDTO> findById(@PathVariable String id) {
        RecipesResponseDTO response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Adds a new recipe!",
            description = "Adds a new Recipe by passing in a JSON representation of the recipe!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<RecipesResponseDTO> save(@RequestBody @Valid RecipesRequestDTO request) {
        RecipesResponseDTO response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/title/{id}")
    @Operation(operationId = "id", summary = "Updates a specific Recipe title by his ID!", description = "Updates a specific Recipe title by his ID!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<RecipesResponseDTO> updateTitle(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateTitle(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/ingredients/{id}")
    @Operation(operationId = "id", summary = "Updates a specific Recipe ingredient by his ID!", description = "Updates a specific Recipe ingredient by his ID!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<RecipesResponseDTO> updateIngredients(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateIngredients(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/method/{id}")
    @Operation(operationId = "id", summary = "Updates a specific Recipe method preparation by his ID!", description = "Updates a specific Recipe method preparation by his ID!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<RecipesResponseDTO> updateMethodPreparation(@RequestBody RecipesRequestDTO request, @PathVariable String id) {
        RecipesResponseDTO response = service.updateMethodPreparation(request, id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/disable/{id}")
    @Operation(operationId = "id", summary = "Disable a specific Recipe by his ID!", description = "Disable a specific Recipe by his ID!",
            tags = {"Recipes"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content (schema = @Schema(implementation = RecipesResponseDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<?> disableRecipe(@PathVariable String id) {
        service.disableRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
