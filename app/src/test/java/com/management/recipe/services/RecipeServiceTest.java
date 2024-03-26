package com.management.recipe.services;

import com.management.recipe.config.MessageProvider;
import com.management.recipe.exceptions.NotFoundException;
import com.management.recipe.model.RecipeRequest;
import com.management.recipe.model.RecipeResponse;
import com.management.recipe.model.RecipeSearchRequest;
import com.management.recipe.model.RecipeType;
import com.management.recipe.models.Ingredient;
import com.management.recipe.models.Recipe;
import com.management.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private MessageProvider messageProvider;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void test_createRecipe_successfully() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(getRecipe());
        when(ingredientService.getIngredientsByIds(any())).thenReturn(getIngredients());
        RecipeResponse recipeResponse = recipeService.createRecipe(getRecipeRequest());
        Assertions.assertEquals(recipeResponse.getName(), "Pasta");
    }


    @Test
    public void test_updateRecipe_successfully() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(getRecipe());
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(getRecipe()));
        RecipeRequest recipeRequest = getRecipeRequest();
        recipeRequest.setId(1);
        when(ingredientService.getIngredientsByIds(any())).thenReturn(getIngredients());
        RecipeResponse recipeResponse = recipeService.updateRecipe(recipeRequest);
        Assertions.assertEquals(recipeResponse.getName(), "Pasta");
        Assertions.assertEquals(recipeResponse.getId(), 1);
    }

    @Test
    public void test_updateRecipe_notFound() {
        when(recipeRepository.findById(anyInt())).thenThrow(NotFoundException.class);
        RecipeRequest recipeRequest = getRecipeRequest();
        recipeRequest.setId(1);
        Assertions.assertThrows(NotFoundException.class, () -> recipeService.updateRecipe(recipeRequest));
    }

    @Test
    public void test_deleteRecipe_successfully() {
        when(recipeRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(anyInt());
        recipeService.deleteRecipe(1);
    }

    @Test
    public void test_deleteRecipe_notFound() {
        when(recipeRepository.existsById(anyInt())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> recipeService.deleteRecipe(1));
    }

    @Test
    public void test_findBySearchCriteria_notFound() {
        RecipeSearchRequest recipeSearchRequest = mock(RecipeSearchRequest.class);
        Assertions.assertThrows(NotFoundException.class, () -> recipeService.findBySearchCriteria(recipeSearchRequest,0,10));
    }

    private static Set<Ingredient> getIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1);
        ingredient1.setCreatedAt(LocalDateTime.now());
        ingredient1.setIngredient("Tomato");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2);
        ingredient2.setCreatedAt(LocalDateTime.now());
        ingredient2.setIngredient("Onion");
        return Set.of(ingredient1, ingredient2);
    }


    private static Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setId(1);
        recipe.setInstructions("instructions");
        recipe.setNumberOfServings(2);
        recipe.setRecipeIngredients(getIngredients());
        return recipe;
    }

    private static RecipeRequest getRecipeRequest() {
        RecipeRequest request = new RecipeRequest();
        request.setName("Pasta");
        request.setType(RecipeType.VEGETARIAN);
        request.setInstructions("ins");
        request.setIngredients(List.of(1, 2));
        request.setNumberOfServings(2);
        return request;
    }
}