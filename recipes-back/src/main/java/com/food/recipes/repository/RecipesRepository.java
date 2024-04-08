package com.food.recipes.repository;

import com.food.recipes.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipesRepository extends JpaRepository<Recipes, String> {

    List<Recipes> findByNameContainsIgnoreCase(String name);

}