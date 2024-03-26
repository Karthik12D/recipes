package com.management.recipe.services;

import com.management.recipe.config.MessageProvider;
import com.management.recipe.exceptions.NotFoundException;
import com.management.recipe.model.IngredientRequest;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.models.Ingredient;
import com.management.recipe.repositories.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private MessageProvider messageProvider;
    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void test_createIngredient_successfully() {
        IngredientRequest request = new IngredientRequest();
        request.name("Tomato");
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setIngredient("Tomato");
        ingredient.setCreatedAt(LocalDateTime.now());
        when(ingredientRepository.save(any())).thenReturn(ingredient);
        IngredientResponse ingredientResponse = ingredientService.create(request);
        Assertions.assertEquals(ingredient.getId(), ingredientResponse.getId());
        Assertions.assertEquals(ingredient.getIngredient(), ingredientResponse.getName());
        Assertions.assertEquals(ingredient.getCreatedAt(), ingredientResponse.getCreatedDate().toLocalDateTime());
    }

    @Test
    public void test_findById_successfully() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setIngredient("Tomato");
        ingredient.setCreatedAt(LocalDateTime.now());
        when(ingredientRepository.findById(anyInt())).
                thenReturn(java.util.Optional.of(ingredient));
        IngredientResponse ingredientResponse = ingredientService.findById(1);
        Assertions.assertEquals(ingredient.getId(), ingredientResponse.getId());
        Assertions.assertEquals(ingredient.getIngredient(), ingredientResponse.getName());
        Assertions.assertEquals(ingredient.getCreatedAt(), ingredientResponse.getCreatedDate().toLocalDateTime());
    }

    @Test
    public void test_findById_notFound() {
        when(ingredientRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> ingredientService.findById(1),
                messageProvider.getMessage("ingredient.notFound"));
    }

    @Test
    public void test_getAllIngredients_successfully() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setIngredient("Tomato");
        ingredient.setCreatedAt(LocalDateTime.now());
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Ingredient> page = mock(Page.class);
        when(page.getContent()).thenReturn(List.of(ingredient));
        when(ingredientRepository.findAll(pageRequest)).thenReturn(page);
        List<IngredientResponse> ingredientResponses = ingredientService.getAllIngredients(0, 10);
        Assertions.assertNotNull(ingredientResponses);
        Assertions.assertEquals(1, ingredientResponses.size());
        IngredientResponse ingredientResponse = ingredientResponses.get(0);
        Assertions.assertEquals(ingredient.getId(), ingredientResponse.getId());
        Assertions.assertEquals(ingredient.getIngredient(), ingredientResponse.getName());
        Assertions.assertEquals(ingredient.getCreatedAt(), ingredientResponse.getCreatedDate().toLocalDateTime());
    }

    @Test
    public void test_deleteIngredient_successfully() {
        when(ingredientRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(ingredientRepository).deleteById(anyInt());
        ingredientService.delete(5);
    }

    @Test
    public void test_deleteIngredient_notFound() {
        when(ingredientRepository.existsById(anyInt())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> ingredientService.delete(1),
                messageProvider.getMessage("ingredient.notFound"));
    }
}