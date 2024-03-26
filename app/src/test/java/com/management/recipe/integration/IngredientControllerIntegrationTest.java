package com.management.recipe.integration;

import com.management.recipe.RecipeApplication;
import com.management.recipe.model.IngredientRequest;
import com.management.recipe.model.IngredientResponse;
import com.management.recipe.util.IntegrationTestHelper;
import com.management.recipe.utils.builder.IngredientTestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RecipeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext
public class IngredientControllerIntegrationTest extends IntegrationTestHelper {
    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void test_createIngredient_successfully() throws Exception {
        IngredientRequest request = IngredientTestDataBuilder.createIngredientRequest();

       mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ingredient")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    public void test_listIngredient_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/" + 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    public void test_listIngredient_notFound() throws Exception {
        mockMvc.perform(get(("/api/v1/ingredient/1123")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_listIngredients_successfully() throws Exception {
        MvcResult result = mockMvc.perform(get(("/api/v1/ingredient/page/0/10")))
                .andExpect(status().isOk())
                .andReturn();

        List<IngredientResponse> responses = getListFromMvcResult(result, IngredientResponse.class);
        Assertions.assertEquals(4, responses.size());
    }

    @Test
    public void test_deleteIngredients_successfully() throws Exception {
        mockMvc.perform(delete(("/api/v1/ingredient/" + 104)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_findIngredientById_successfully() throws Exception {
        mockMvc.perform(get(("/api/v1/ingredient/" + 101)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    public void test_findIngredientById_fails() throws Exception {

        mockMvc.perform(get(("/api/v1/ingredient/" + 145)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_deleteIngredient_notFound() throws Exception {

        mockMvc.perform(delete(("/api/v1/ingredient/11")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
