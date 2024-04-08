package com.food.recipes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipesRequestDTO {

    private String name;
    private String title;
    private String ingredients;
    private String methodPreparation;

}
