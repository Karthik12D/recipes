package com.management.recipe.controllers;

import com.management.recipe.api.IngredientApi;
import com.management.recipe.model.IngredientRequest;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.services.IngredientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class IngredientController implements IngredientApi {
    private final IngredientService ingredientService;

    /**
     * Get all ingredients
     * @param page Page number (required)
     * @param size Page size (required)
     * @return List of ingredients
     */
    @Override
    public ResponseEntity<List<IngredientResponse>> getAllIngredients(@PathVariable(name = "page") Integer page,
                                                                                                 @PathVariable(name = "size") Integer size) {
        log.info("Getting the ingredients");
        return ResponseEntity.ok(ingredientService.getAllIngredients(page, size));
    }

    /**
     *  Get the ingredient by its id
     * @param ingredientId ID of the ingredient to return (required)
     * @return IngredientResponse
     */
   @Override
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable(name = "ingredientId") Integer ingredientId) {
        log.info("Getting the ingredient by its id. Id: {}", ingredientId);
        return ResponseEntity.ok(ingredientService.findById(ingredientId));
    }

    /**
     *  Create a new ingredient
     * @param request  (optional)
     * @return  (status code 201) with created ingredient
     */
    @Override
    public ResponseEntity<IngredientResponse> addIngredient(@Valid @RequestBody IngredientRequest request) {
        log.info("Creating the ingredient with properties");
        return new ResponseEntity<>(ingredientService.create(request), HttpStatus.CREATED);
    }

    /**
     *  delete the ingredient by its id
     * @param ingredientId ID of the ingredient to delete (required)
     * @return  (status code 204)
     */
    @Override
    public ResponseEntity<Void> deleteIngredientById(@PathVariable(name = "ingredientId") Integer ingredientId) {
        log.info("Deleting the ingredient by its id. Id: {}", ingredientId);
        ingredientService.delete(ingredientId);
        return ResponseEntity.noContent().build();
    }
}
