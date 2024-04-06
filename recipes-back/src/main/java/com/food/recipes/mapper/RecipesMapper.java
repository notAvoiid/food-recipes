package com.food.recipes.mapper;

import com.food.recipes.model.Recipes;
import com.food.recipes.model.dto.RecipesRequestDTO;
import com.food.recipes.model.dto.RecipesResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipesMapper {

    private final ModelMapper mapper;

    public RecipesMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Recipes toEntity(RecipesRequestDTO request) {
        return mapper.map(request, Recipes.class);
    }

    public RecipesResponseDTO entityToDto(Recipes recipes) {
        return mapper.map(recipes, RecipesResponseDTO.class);
    }

    public List<RecipesResponseDTO> ListEntityToListDto(List<Recipes> recipes) {
        return recipes
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
