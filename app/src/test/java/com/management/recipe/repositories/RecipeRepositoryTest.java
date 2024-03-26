package com.management.recipe.repositories;

import com.management.recipe.model.RecipeType;
import com.management.recipe.models.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RecipeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void test_whenTryToSaveIngredientSuccess() {
        Recipe entity = new Recipe();
        entity.setType(RecipeType.VEGETARIAN.name());
        entity.setInstructions("some instructions");
        entity.setName("pasta");
        Recipe savedRecipe = recipeRepository.save(entity);
        Recipe recipe = entityManager.find(Recipe.class, savedRecipe.getId());
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getType(), recipe.getType());
        Assertions.assertEquals(savedRecipe.getId(), recipe.getId());
    }

    @Test
    public void test_whenTryGetAllRecipeSuccess() {
        Recipe entity1 = new Recipe();
        entity1.setType(RecipeType.VEGETARIAN.name());
        entity1.setName("lasagna");
        entity1.setInstructions("some instructions");
        Recipe entity2 = new Recipe();
        entity2.setType("Other");
        entity2.setName("pizza");
        entity2.setInstructions("some instructions");
        Recipe firstSavedEntity = recipeRepository.save(entity1);
        Recipe secondSavedEntity = recipeRepository.save(entity2);
        Assertions.assertNotNull(firstSavedEntity);
        Assertions.assertNotNull(secondSavedEntity);

        Assertions.assertFalse(recipeRepository.findAll().isEmpty());
        Assertions.assertEquals(3, recipeRepository.findAll().size());
    }

    @Test
    public void test_whenTryToUpdateRecipeSuccess() {
        Recipe entity = new Recipe();
        entity.setType(RecipeType.VEGETARIAN.name());
        entity.setName("lasagna");
        entity.setInstructions("some instructions");
        Recipe savedRecipe = recipeRepository.save(entity);
        Recipe recipe = entityManager.find(Recipe.class, savedRecipe.getId());
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getType(), recipe.getType());
        Assertions.assertEquals(savedRecipe.getId(), recipe.getId());

        recipe.setType(RecipeType.VEGAN.name());
        Recipe updatedRecipe = recipeRepository.save(recipe);
        Recipe updatedRecipeFromDb = entityManager.find(Recipe.class, updatedRecipe.getId());
        Assertions.assertEquals(updatedRecipe.getType(), updatedRecipeFromDb.getType());
    }

    @Test
    public void test_whenTryToDeleteRecipeSuccess() {
        Recipe entity = new Recipe();
        entity.setType(RecipeType.VEGETARIAN.name());
        entity.setName("lasagna");
        entity.setInstructions("some instructions");
        Recipe savedRecipe = recipeRepository.save(entity);
        Recipe recipe = entityManager.find(Recipe.class, savedRecipe.getId());
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getType(), recipe.getType());
        Assertions.assertEquals(savedRecipe.getId(), recipe.getId());

        recipeRepository.deleteById(recipe.getId());
        Assertions.assertFalse(recipeRepository.existsById(recipe.getId()));
    }

    @Test
    public void test_whenTryToGetRecipeByIdSuccess() {
        Recipe entity = new Recipe();
        entity.setType(RecipeType.VEGETARIAN.name());
        entity.setName("lasagna");
        entity.setInstructions("some instructions");
        Recipe savedRecipe = recipeRepository.save(entity);
        Recipe recipe = entityManager.find(Recipe.class, savedRecipe.getId());
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getType(), recipe.getType());
        Assertions.assertEquals(savedRecipe.getId(), recipe.getId());

        Recipe recipeById = recipeRepository.findById(recipe.getId()).get();
        Assertions.assertEquals(recipe.getId(), recipeById.getId());
    }

    @Test
    public void test_whenTryToGetRecipeByIdFails() {
        Recipe entity = new Recipe();
        entity.setType(RecipeType.VEGETARIAN.name());
        entity.setName("lasagna");
        entity.setInstructions("some instructions");
        Recipe savedRecipe = recipeRepository.save(entity);
        Recipe recipe = entityManager.find(Recipe.class, savedRecipe.getId());
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getType(), recipe.getType());
        Assertions.assertEquals(savedRecipe.getId(), recipe.getId());

        assertThrows(Exception.class, () -> recipeRepository.findById(1000).get());
    }
}