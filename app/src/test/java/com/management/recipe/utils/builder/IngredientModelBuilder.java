package com.management.recipe.utils.builder;

import com.management.recipe.models.Ingredient;

import java.time.LocalDateTime;

public class IngredientModelBuilder {
    private Integer id;
    private String ingredientName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ingredient build() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setIngredient(ingredientName);
        ingredient.setCreatedAt(createdAt);
        ingredient.setUpdatedAt(updatedAt);

        return ingredient;
    }

    public IngredientModelBuilder withName(String name) {
        this.ingredientName = name;
        return this;
    }
}
