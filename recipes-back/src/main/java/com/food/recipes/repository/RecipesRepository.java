package com.food.recipes.repository;

import com.food.recipes.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipesRepository extends JpaRepository<Recipes, UUID> {

    List<Recipes> findByNameContainsIgnoreCase(String name);

}