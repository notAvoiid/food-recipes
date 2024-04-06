package com.food.recipes.service;

import com.food.recipes.controller.RecipesController;
import com.food.recipes.exceptions.EntityNotFoundException;
import com.food.recipes.mapper.RecipesMapper;
import com.food.recipes.model.Recipes;
import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import com.food.recipes.repository.RecipesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class RecipesService {

    private final RecipesRepository repository;
    private final RecipesMapper mapper;

    public RecipesService(RecipesRepository repository, RecipesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<RecipesResponseDTO> findAll() {
        List<Recipes> recipes = repository.findAll();
        List<RecipesResponseDTO> responses = mapper.ListEntityToListDto(recipes);
        responses.forEach(r -> r.add(linkTo(methodOn(RecipesController.class).findById(r.getId())).withSelfRel()));
        log.info("Finding all recipes");

        return responses;
    }

    @Transactional(readOnly = true)
    public RecipesResponseDTO findById(UUID id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found", id))
        );
        RecipesResponseDTO response = mapper.entityToDto(recipes);
        response.add(linkTo(methodOn(RecipesController.class).findAll()).withSelfRel());
        log.info("Finding a recipe by his id:" + id);

        return response;
    }

    @Transactional
    public RecipesResponseDTO save(RecipesRequestDTO request) {
        Recipes recipes = mapper.toEntity(request);
        RecipesResponseDTO response = mapper.entityToDto(repository.save(recipes));
        response.add(linkTo(methodOn(RecipesController.class).findById(response.getId())).withSelfRel());
        log.info("Saving a recipe");

        return response;
    }
}
