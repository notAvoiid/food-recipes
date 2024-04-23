package com.food.recipes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Entity(name = "recipes")
@Table
@Getter
@Setter
public class Recipes extends RepresentationModel<Recipes> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipes_id", nullable = false)
    private String id;

    @Column(name = "recipes_title", nullable = false, length = 30)
    private String title;

    @Column(name = "recipes_ingredients", nullable = false, length = 25)
    private String ingredients;

    @Column(name = "recipes_method_preparation", nullable = false)
    private String methodPreparation;

    @Column(name = "recipes_image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Boolean enabled;

    public Recipes(String id, String title, String ingredients, String methodPreparation, String imageUrl, Boolean enabled) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.methodPreparation = methodPreparation;
        this.imageUrl = imageUrl;
        this.enabled = true;
    }

    public Recipes() {
        this.enabled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipes recipes = (Recipes) o;
        return Objects.equals(id, recipes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
