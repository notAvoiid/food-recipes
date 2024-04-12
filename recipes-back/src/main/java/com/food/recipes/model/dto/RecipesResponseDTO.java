package com.food.recipes.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter
public class RecipesResponseDTO extends RepresentationModel<RecipesResponseDTO> {

    private String id;
    private String title;
    private String ingredients;
    private String methodPreparation;
    private Boolean enabled;

}
