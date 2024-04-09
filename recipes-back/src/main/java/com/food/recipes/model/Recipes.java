package com.food.recipes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Entity(name = "recipes")
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipes extends RepresentationModel<Recipes> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipes_id", nullable = false)
    private String id;

    @Column(name = "recipes_title", nullable = false, length = 30)
    private String title;

    @Column(name = "recipes_name", nullable = false, length = 30)
    private String name;

    @Column(name = "recipes_ingredients", nullable = false, length = 25)
    private String ingredients;

    @Column(name = "recipes_method_preparation", nullable = false)
    private String methodPreparation;

    @Column(nullable = false)
    private Boolean enabled = true;

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
