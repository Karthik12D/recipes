package com.management.recipe.services;

import com.management.recipe.config.MessageProvider;
import com.management.recipe.exceptions.NotFoundException;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.model.RecipeRequest;
import com.management.recipe.model.RecipeResponse;
import com.management.recipe.model.RecipeSearchRequest;
import com.management.recipe.models.Ingredient;
import com.management.recipe.models.Recipe;
import com.management.recipe.repositories.RecipeRepository;
import com.management.recipe.search.RecipeSpecificationBuilder;
import com.management.recipe.search.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final MessageProvider messageProvider;

    public RecipeResponse createRecipe(final RecipeRequest createRecipeRequest) {
        Set<Ingredient> ingredients = Optional.ofNullable(createRecipeRequest.getIngredients())
                .map(ingredientService::getIngredientsByIds)
                .orElse(null);
        Recipe recipe = createRecipeEntity(createRecipeRequest, ingredients);
        return generateResponse(recipeRepository.save(recipe));
    }

    private static Recipe createRecipeEntity(RecipeRequest createRecipeRequest, Set<Ingredient> ingredients) {
        Recipe recipe = new Recipe();
        recipe.setName(createRecipeRequest.getName());
        recipe.setInstructions(createRecipeRequest.getInstructions());
        recipe.setType(createRecipeRequest.getType().name());
        recipe.setNumberOfServings(createRecipeRequest.getNumberOfServings());
        recipe.setRecipeIngredients(ingredients);
        return recipe;
    }

    public List<RecipeResponse> getAllRecipes(final int page, final int size) {
        Pageable pageRequest
                = PageRequest.of(page, size);
        List<Recipe> recipes = recipeRepository.findAll(pageRequest).getContent();
        return recipes.stream().map(recipe -> generateResponse(recipe)).collect(Collectors.toList());
    }

    public RecipeResponse getRecipeById(final int id) {
        Recipe recipe =  recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageProvider.getMessage("recipe.notFound")));
         return generateResponse(recipe);
    }

    public RecipeResponse updateRecipe(final RecipeRequest updateRecipeRequest) {
        Recipe recipe = recipeRepository.findById(updateRecipeRequest.getId())
                .orElseThrow(() -> new NotFoundException(messageProvider.getMessage("recipe.notFound")));

        Set<Ingredient> ingredients = Optional.ofNullable(updateRecipeRequest.getIngredients())
                .map(ingredientService::getIngredientsByIds)
                .orElse(null);

        recipe.setName(updateRecipeRequest.getName());
        recipe.setType(updateRecipeRequest.getType().name());
        recipe.setNumberOfServings(updateRecipeRequest.getNumberOfServings());
        recipe.setInstructions(updateRecipeRequest.getInstructions());
        if (Optional.ofNullable(ingredients).isPresent()) recipe.setRecipeIngredients(ingredients);
        return generateResponse(recipeRepository.save(recipe));
    }

    public void deleteRecipe(final int id) {
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException(messageProvider.getMessage("recipe.notFound"));
        }
        recipeRepository.deleteById(id);
    }

    public List<RecipeResponse> findBySearchCriteria(com.management.recipe.model.RecipeSearchRequest recipeSearchRequest, int page, int size) {
        List<SearchCriteria> searchCriterionRequests = new ArrayList<>();
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(searchCriterionRequests);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("name")
                .ascending());

        Specification<Recipe> recipeSpecification = createRecipeSpecification(recipeSearchRequest, builder);
        Page<Recipe> filteredRecipes = recipeRepository.findAll(recipeSpecification, pageRequest);

        return filteredRecipes.toList().stream()
                .map(recipe -> generateResponse(recipe))
                .collect(Collectors.toList());
    }

    private Specification<Recipe> createRecipeSpecification(RecipeSearchRequest recipeSearchRequest,
                                                            RecipeSpecificationBuilder builder) {
        List<com.management.recipe.model.SearchCriteria> searchCriteriaRequests = recipeSearchRequest.getSearchCriteria();

        if (Optional.ofNullable(searchCriteriaRequests).isPresent()) {
            List<SearchCriteria> searchCriteriaList = searchCriteriaRequests.stream()
                    .map(SearchCriteria::new)
                    .toList();

            if (!searchCriteriaList.isEmpty()) searchCriteriaList.forEach(criteria -> {
                criteria.setDataOption(recipeSearchRequest.getDataOption().name());
                builder.with(criteria);
            });
        }
        return builder
                .build()
                .orElseThrow(() -> new NotFoundException(messageProvider.getMessage("criteria.notFound")));
    }

    private RecipeResponse generateResponse(final Recipe recipe) {
        RecipeResponse recipeResponse = new com.management.recipe.model.RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setName(recipe.getName());
        recipeResponse.setInstructions(recipe.getInstructions());
        recipeResponse.setType(recipe.getType());
        recipeResponse.setNumberOfServings(recipe.getNumberOfServings());
        recipeResponse.setIngredients(generateIngredientsResponse(recipe.getRecipeIngredients()));
        return recipeResponse;
    }

    private List<IngredientResponse> generateIngredientsResponse(final Set<Ingredient> ingredients) {
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            IngredientResponse ingredientResponse = new IngredientResponse();
            ingredientResponse.setId(ingredient.getId());
            ingredientResponse.setName(ingredient.getIngredient());
            ingredientResponse.setCreatedDate(ingredient.getCreatedAt().atZone(ZoneId.systemDefault()).toOffsetDateTime());
            ingredientResponses.add(ingredientResponse);
        });
        return ingredientResponses;
    }
}
