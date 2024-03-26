package com.management.recipe.repositories;

import com.management.recipe.models.Ingredient;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class IngredientRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void test_whenTryToSaveIngredientSuccess() {
        Ingredient entity = new Ingredient();
        entity.setIngredient("Tomato");
        Ingredient savedIngredient = ingredientRepository.save(entity);
        assertNotNull(savedIngredient);
        Ingredient ingredient = entityManager.find(Ingredient.class, savedIngredient.getId());
        Assertions.assertEquals(savedIngredient.getIngredient(), ingredient.getIngredient());
        Assertions.assertNotNull(savedIngredient.getId());
    }

    @Test
    public void test_whenTryGetIngredientsSuccess() {
        Ingredient entity1 = new Ingredient();
        entity1.setIngredient("Tomato");
        Ingredient entity2 = new Ingredient();
        entity2.setIngredient("Potato");
        Ingredient firstSavedEntity = ingredientRepository.save(entity1);
        Ingredient secondSavedEntity = ingredientRepository.save(entity2);
        Assertions.assertNotNull(firstSavedEntity);
        Assertions.assertNotNull(secondSavedEntity);

        Assertions.assertFalse(ingredientRepository.findAll().isEmpty());
        Assertions.assertEquals(6, ingredientRepository.findAll().size());
    }

    @Test
    public void test_whenTryAddSameIngredientTwiceFails() {
        Optional<Ingredient> ingredient = ingredientRepository.findById(101);
        Assertions.assertEquals(101, ingredient.get().getId());
    }

    @Test
    public void test_whenTryAddIngredientWithNullNameFails() {
        Ingredient entity = new Ingredient();
        assertThrows(ConstraintViolationException.class, () -> ingredientRepository.save(entity));
    }

    @Test
    public void test_whenTryDeleteIngredient() {
        ingredientRepository.delete(ingredientRepository.findById(101).get());
    }
}