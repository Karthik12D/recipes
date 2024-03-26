package com.management.recipe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.model.RecipeRequest;
import com.management.recipe.model.RecipeResponse;
import com.management.recipe.model.RecipeType;
import com.management.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RecipeController(recipeService)).build();
    }

    @Test
    public void test_createRecipe_successfully() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("pasta");
        request.setType(RecipeType.VEGETARIAN);
        request.setInstructions("instructions");
        request.setNumberOfServings(2);
        request.setIngredients(List.of(1));
        when(recipeService.createRecipe(any())).thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pasta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("instructions"));
    }


    @Test
    public void test_listRecipe_successfully() throws Exception {
        when(recipeService.getAllRecipes(anyInt(), anyInt())).thenReturn(getAllRecipesResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe/page/1/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("pasta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].instructions").value("instructions"));
    }

    @Test
    public void test_updateRecipe_successfully() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("pasta");
        request.setType(RecipeType.VEGETARIAN);
        request.setInstructions("instructions");
        request.setNumberOfServings(2);
        request.setId(1);
        request.setIngredients(List.of(1));
        List<Integer> ingredients = new ArrayList<>() {{ add(1);}};
        request.setIngredients(ingredients);
        request.setId(1);
        when(recipeService.updateRecipe(any(RecipeRequest.class))).thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pasta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("instructions"));
    }

    @Test
    public void test_deleteRecipe_successfully() throws Exception {
        doNothing().when(recipeService).deleteRecipe(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recipe/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_getRecipeById_successfully() throws Exception {
        when(recipeService.getRecipeById(anyInt())).thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pasta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("instructions"));
    }

    @Test
    public void test_createRecipe_withInvalidRequest() throws Exception {
        RecipeRequest request = new RecipeRequest();
        //request.setName("pasta");
        request.setType(RecipeType.VEGETARIAN);
        request.setInstructions("instructions");
        request.setNumberOfServings(2);
        request.setIngredients(List.of(1));
        List<Integer> ingredients = new ArrayList<>() {{
            add(1);
        }};
        request.setIngredients(ingredients);
        when(recipeService.createRecipe(any())).thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateRecipe_withInvalidRequest() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("pasta");
        request.setType(RecipeType.VEGETARIAN);
        request.setInstructions("instructions");
        //request.setNumberOfServings(2);
        request.setIngredients(List.of(1));
        List<Integer> ingredients = new ArrayList<>() {{ add(1);}};
        request.setIngredients(ingredients);
        request.setId(1);
        when(recipeService.updateRecipe(any(RecipeRequest.class))).thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getRecipeById_withInvalidRequest() throws Exception {
        when(recipeService.getRecipeById(anyInt())).
                thenReturn(getRecipeResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe?0"))
                .andExpect(status().isMethodNotAllowed());
    }

    private List<RecipeResponse> getAllRecipesResponse() {
        List<RecipeResponse> recipes = new ArrayList<>();
        recipes.add(getRecipeResponse());
        return recipes;
    }

    private RecipeResponse getRecipeResponse() {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(1);
        recipeResponse.setName("pasta");
        recipeResponse.createdDate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toOffsetDateTime());
        recipeResponse.setInstructions("instructions");
        recipeResponse.setType(RecipeType.VEGETARIAN.name());
        recipeResponse.numberOfServings(2);
        recipeResponse.setIngredients(getAllIngredientsResponse());
        return recipeResponse;
    }

    private List<IngredientResponse> getAllIngredientsResponse() {
        List<IngredientResponse> ingredients = new ArrayList<>();
        ingredients.add(getIngredientResponse());
        return ingredients;
    }
    private IngredientResponse getIngredientResponse() {
        IngredientResponse ingredientResponse = new IngredientResponse();
        ingredientResponse.setId(1);
        ingredientResponse.setName("ingredient1");
        return ingredientResponse;
    }
}