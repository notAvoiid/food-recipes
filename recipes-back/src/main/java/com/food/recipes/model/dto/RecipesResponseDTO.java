package com.food.recipes.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class RecipesResponseDTO extends RepresentationModel<RecipesResponseDTO> {

    private UUID id;
    private String name;

}
