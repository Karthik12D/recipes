package com.management.recipe.controllers;

import com.management.recipe.api.RecipeApi;
import com.management.recipe.model.RecipeRequest;
import com.management.recipe.model.RecipeResponse;
import com.management.recipe.model.RecipeSearchRequest;
import com.management.recipe.services.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class RecipeController implements RecipeApi {
    private final RecipeService recipeService;

    /**
     * Get all recipes
     * @param page Page number (required)
     * @param size Page size (required)
     * @return List of recipes
     */
    @Override
    public ResponseEntity<List<RecipeResponse>> getAllRecipes(@PathVariable(name = "page") Integer page,
                                                                                          @PathVariable(name = "size") Integer size) {
        log.info("Getting the recipes");
        return ResponseEntity.ok(recipeService.getAllRecipes(page, size));
    }

    /**
     * Get the recipe by its id
     * @param recipeId ID of the recipe to return (required)
     * @return RecipeResponse
     */
   @Override
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable(name = "recipeId") Integer recipeId) {
        log.info("Getting the recipe by its id. Id: {}", recipeId);
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }

    /**
     * Create a new recipe
     * @param request  (optional)
     * @return (status code 201) with created recipe
     */
    @Override
    public ResponseEntity<RecipeResponse> addRecipe(@RequestBody RecipeRequest request) {
        log.info("Creating the recipe with properties");
        return new ResponseEntity<>(recipeService.createRecipe(request), HttpStatus.CREATED);
    }

    /**
     * Update the recipe by given properties
     * @param updateRecipeRequest  (optional)
     * @return (status code 200) with updated recipe
     */
    @Override
    public ResponseEntity<RecipeResponse> updateRecipe(@Valid @RequestBody RecipeRequest updateRecipeRequest) {
        log.info("Updating the recipe by given properties");
        return ResponseEntity.ok(recipeService.updateRecipe(updateRecipeRequest));
    }

    /**
     * Delete the recipe by its id
     * @param recipeId ID of the recipe to delete (required)
     * @return (status code 204)
     */
    @Override
    public ResponseEntity<Void> deleteRecipeById(@PathVariable(name = "recipeId") Integer recipeId) {
        log.info("Deleting the recipe by its id. Id: {}", recipeId);
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search the recipes by given criteria
     * @param page Page number (required)
     * @param size Page size (required)
     * @param recipeSearchRequest  (optional)
     * @return List of recipes
     */
    @Override
    public ResponseEntity<List<RecipeResponse>> searchRecipes(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "10") Integer size,
                                             @RequestBody RecipeSearchRequest recipeSearchRequest) {
        log.info("Searching the recipe by given criteria");
        return ResponseEntity.ok(recipeService.findBySearchCriteria(recipeSearchRequest, page, size));
    }
}
