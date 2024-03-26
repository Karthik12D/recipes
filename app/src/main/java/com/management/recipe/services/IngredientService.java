package com.management.recipe.services;

import com.management.recipe.config.MessageProvider;
import com.management.recipe.exceptions.NotFoundException;
import com.management.recipe.model.IngredientRequest;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.models.Ingredient;
import com.management.recipe.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final MessageProvider messageProvider;

    public IngredientResponse create(IngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient(request.getName());
        return getIngredientResponse(ingredientRepository.save(ingredient));
    }

    public Set<Ingredient> getIngredientsByIds(List<Integer> ingredientIds) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientIds.get(0));
        return ingredientIds.stream()
                .map(ingredientId -> ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new NotFoundException(messageProvider.getMessage("ingredient.notFound"))))
                .collect(Collectors.toSet());
    }

    public IngredientResponse findById(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageProvider.getMessage("ingredient.notFound")));
        return getIngredientResponse(ingredient);
    }

    public List<IngredientResponse> getAllIngredients(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Ingredient> ingredients = ingredientRepository.findAll(pageRequest);
        return generateIngredientsResponse(ingredients.getContent());
    }

    public void delete(Integer id) {
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException(messageProvider.getMessage("ingredient.notFound"));
        }
        ingredientRepository.deleteById(id);
    }

    private List<IngredientResponse> generateIngredientsResponse(List<Ingredient> ingredients) {
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            ingredientResponses.add(getIngredientResponse(ingredient));
        });
        return ingredientResponses;
    }

    private static IngredientResponse getIngredientResponse(Ingredient ingredient) {
        IngredientResponse ingredientResponse = new IngredientResponse();
        ingredientResponse.setId(ingredient.getId());
        ingredientResponse.setName(ingredient.getIngredient());
        ingredientResponse.setCreatedDate(ingredient.getCreatedAt() != null ? ingredient.getCreatedAt().atZone(ZoneId.systemDefault()).toOffsetDateTime() : null);
        return ingredientResponse;
    }
}
