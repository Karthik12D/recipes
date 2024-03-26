package com.management.recipe.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.recipe.model.IngredientRequest;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.services.IngredientService;
import com.management.recipe.utils.builder.IngredientTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientControllerTest {
    @Mock
    private IngredientService ingredientService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(ingredientService)).build();
    }

    @Test
    public void test_createIngredient_successfully() throws Exception {
        when(ingredientService.create(any())).thenReturn(getIngredientResponse());
        IngredientRequest request = IngredientTestDataBuilder.createIngredientRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ingredient1"));
    }

    @Test
    public void test_getIngredient_successfully() throws Exception {
        when(ingredientService.findById(1)).thenReturn(getIngredientResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ingredient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ingredient1"));
    }

    @Test
    public void test_GetAllIngredients_successfully() throws Exception {
        when(ingredientService.getAllIngredients(anyInt(), anyInt())).thenReturn(getAllIngredientsResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ingredient/page/1/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ingredient1"));
    }

    @Test
    public void test_deleteIngredient_successfully() throws Exception {
        doNothing().when(ingredientService).delete(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/ingredient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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