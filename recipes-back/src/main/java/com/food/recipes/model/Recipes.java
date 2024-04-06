package com.food.recipes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "recipes")
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipes extends RepresentationModel<Recipes> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

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
