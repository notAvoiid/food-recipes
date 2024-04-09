package com.food.recipes.repository;

import com.food.recipes.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipesRepository extends JpaRepository<Recipes, String> {

    List<Recipes> findByNameContainsIgnoreCase(String name);

    @Modifying
    @Query("UPDATE recipes r SET r.enabled = false WHERE r.id =:id")
    void disableRecipe(String id);
}