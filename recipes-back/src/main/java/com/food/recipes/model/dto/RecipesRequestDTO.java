package com.food.recipes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipesRequestDTO {

    @NotBlank(message = "Title must not be blank!")
    @Size(min = 4, max = 30, message = "Must be between 4 and 20 characters")
    private String title;

    @NotBlank(message = "Ingredients must not be blank!")
    @Size(min = 3, max = 25, message = "Must be between 3 and 15 characters")
    private String ingredients;

    @NotBlank(message = "Method preparation must not be blank!")
    @Size(min = 5, message = "Method have at least 5 characters")
    private String methodPreparation;
}
