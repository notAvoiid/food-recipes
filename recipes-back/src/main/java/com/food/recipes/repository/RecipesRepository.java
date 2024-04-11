package com.food.recipes.repository;

import com.food.recipes.model.Recipes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecipesRepository extends JpaRepository<Recipes, String> {

    Page<Recipes> findByNameContainsIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Query("UPDATE recipes r SET r.enabled = false WHERE r.id =:id")
    void disableRecipe(String id);
}