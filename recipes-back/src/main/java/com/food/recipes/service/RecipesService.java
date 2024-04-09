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
    public List<RecipesResponseDTO> findByName(String name) {
        List<Recipes> recipes = repository.findByNameContainsIgnoreCase(name);
        List<RecipesResponseDTO> responses = mapper.ListEntityToListDto(recipes);
        responses.forEach(r -> r.add(linkTo(methodOn(RecipesController.class).findById(r.getId())).withSelfRel()));
        log.info("Finding all recipes by name and tags");

        return responses;
    }

    @Transactional(readOnly = true)
    public RecipesResponseDTO findById(String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found", id))
        );
        RecipesResponseDTO response = mapper.entityToDto(recipes);
        response.add(linkTo(methodOn(RecipesController.class).findAll()).withSelfRel());
        log.info("Finding a recipe by his id:{}", id);

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

    @Transactional
    public RecipesResponseDTO updateTitle(RecipesRequestDTO requestDTO, String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found!", id))
        );
        recipes.setTitle(requestDTO.getTitle());
        RecipesResponseDTO response = mapper.entityToDto(repository.save(recipes));
        response.add(linkTo(methodOn(RecipesController.class).findById(response.getId())).withSelfRel());

        return response;
    }

    @Transactional
    public RecipesResponseDTO updateIngredients(RecipesRequestDTO requestDTO, String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found!", id))
        );
        recipes.setIngredients(requestDTO.getIngredients());
        RecipesResponseDTO response = mapper.entityToDto(repository.save(recipes));
        response.add(linkTo(methodOn(RecipesController.class).findById(response.getId())).withSelfRel());

        return response;
    }

    @Transactional
    public RecipesResponseDTO updateMethodPreparation(RecipesRequestDTO requestDTO, String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found!", id))
        );
        recipes.setMethodPreparation(requestDTO.getMethodPreparation());
        RecipesResponseDTO response = mapper.entityToDto(repository.save(recipes));
        response.add(linkTo(methodOn(RecipesController.class).findById(response.getId())).withSelfRel());

        return response;
    }

    @Transactional
    public void disableRecipe(String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found!", id))
        );
        repository.disableRecipe(id);
        RecipesResponseDTO response = mapper.entityToDto(recipes);
        response.add(linkTo(methodOn(RecipesController.class).findById(id)).withSelfRel());
        log.warn("Disabling id:{}", id);
    }
}
