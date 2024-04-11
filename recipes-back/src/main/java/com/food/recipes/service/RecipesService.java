package com.food.recipes.service;

import com.food.recipes.controller.RecipesController;
import com.food.recipes.exceptions.EntityNotFoundException;
import com.food.recipes.mapper.RecipesMapper;
import com.food.recipes.model.Recipes;
import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import com.food.recipes.repository.RecipesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class RecipesService {

    private final RecipesRepository repository;
    private final RecipesMapper mapper;

    @Autowired
    private PagedResourcesAssembler<RecipesResponseDTO> assembler;


    public RecipesService(RecipesRepository repository, RecipesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<RecipesResponseDTO>> findAll(Pageable pageable) {

        var recipesPages = repository.findAll(pageable);
        var recipesDTOPages = recipesPages.map(mapper::entityToDto);
        recipesDTOPages.map(r -> r.add(linkTo(methodOn(RecipesController.class).findById(r.getId())).withSelfRel()));

        Link link = linkTo(methodOn(RecipesController.class).findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "ASC"
        )).withSelfRel();

        log.info("Finding all recipes");

        return assembler.toModel(recipesDTOPages, link);
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<RecipesResponseDTO>> findByName(String name, Pageable pageable) {

        var recipesNamePages = repository.findByNameContainsIgnoreCase(name, pageable);
        var recipesDTOPages = recipesNamePages.map(mapper::entityToDto);
        recipesDTOPages.map(r -> r.add(linkTo(methodOn(RecipesController.class).findById(r.getId())).withSelfRel()));

        Link link = linkTo(methodOn(RecipesController.class).findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "ASC"
        )).withSelfRel();

        log.info("Finding all recipes by name and tags");

        return assembler.toModel(recipesDTOPages, link);
    }

    @Transactional(readOnly = true)
    public RecipesResponseDTO findById(String id) {
        Recipes recipes = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id:%s not found", id))
        );
        RecipesResponseDTO response = mapper.entityToDto(recipes);
        response.add(linkTo(methodOn(RecipesController.class).findById(id)).withSelfRel());
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
