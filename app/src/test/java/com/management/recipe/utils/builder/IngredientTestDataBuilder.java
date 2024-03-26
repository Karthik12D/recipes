package com.management.recipe.utils.builder;

import com.management.recipe.model.IngredientRequest;
import com.management.recipe.models.Ingredient;

public class IngredientTestDataBuilder {
    public static IngredientRequest createIngredientRequest() {
        return new IngredientRequest()
                .name("tomato");
    }

    public static Ingredient createIngredient() {
        return new IngredientModelBuilder()
                .withName("tomato")
                .build();
    }
}
