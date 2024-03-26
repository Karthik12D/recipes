package com.management.recipe.integration;

import com.management.recipe.RecipeApplication;
import com.management.recipe.model.RecipeRequest;
import com.management.recipe.model.RecipeResponse;
import com.management.recipe.model.RecipeSearchRequest;
import com.management.recipe.model.RecipeSearchRequest.DataOptionEnum;
import com.management.recipe.model.RecipeType;
import com.management.recipe.model.SearchCriteria;
import com.management.recipe.model.SearchCriteria.FilterKeyEnum;
import com.management.recipe.model.SearchCriteria.OperationEnum;
import com.management.recipe.util.IntegrationTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RecipeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext
public class RecipeControllerIntegrationTest extends IntegrationTestHelper {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_createRecipe_successfully() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("rice");
        request.setType(RecipeType.NON_VEGETARIAN);
        request.setInstructions("hot and spicy");
        request.setNumberOfServings(10);
        request.setIngredients(List.of(101,102));
                mockMvc.perform(post("/api/v1/recipe")
                                .content(toJson(request))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.name").value("rice"));
    }

    @Test
    public void test_getRecipe_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/recipe/" + 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("Spaghetti"))
                .andExpect(jsonPath("$.instructions").value("spicy"))
                .andExpect(jsonPath("$.numberOfServings").value(2));
    }

    @Test
    public void test_getRecipe_notFound() throws Exception {

        mockMvc.perform(get("/api/v1/recipe/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_listRecipe_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/recipe/page/0/10"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateRecipe_successfully() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("rice");
        request.setType(RecipeType.NON_VEGETARIAN);
        request.setInstructions("hot and spicy");
        request.setNumberOfServings(10);
        request.setIngredients(List.of(101,102));
       MvcResult mvcResult = mockMvc.perform(post("/api/v1/recipe")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();
       RecipeResponse recipeResponse = getFromMvcResult(mvcResult, RecipeResponse.class);
        RecipeRequest updateRequest = new RecipeRequest();
        updateRequest.setName("lasanga");
        updateRequest.setType(RecipeType.NON_VEGETARIAN);
        updateRequest.setInstructions("hot and spicy");
        updateRequest.setNumberOfServings(3);
        updateRequest.setIngredients(List.of(101,102));
        updateRequest.setId(recipeResponse.getId());
        mockMvc.perform(put("/api/v1/recipe")
                .content(toJson(updateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeResponse.getId()))
                .andExpect(jsonPath("$.name").value("lasanga"));
    }

    @Test
    public void test_updateRecipe_idIsNull() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("lasanga");
        request.setType(RecipeType.NON_VEGETARIAN);
        request.setInstructions("hot and spicy");
        request.setNumberOfServings(3);
        request.setIngredients(List.of(101));
        mockMvc.perform(put("/api/v1/recipe")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateRecipe_notFound() throws Exception {
        RecipeRequest request = new RecipeRequest();
        request.setName("lasanga");
        request.setId(23);
        request.setType(RecipeType.NON_VEGETARIAN);
        request.setInstructions("hot and spicy");
        request.setNumberOfServings(3);
        request.setIngredients(List.of(101));
        mockMvc.perform(put("/api/v1/recipe")
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_deleteRecipe_successfully() throws Exception {
        mockMvc.perform(delete("/api/v1/recipe/" + 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deleteRecipe_notFound() throws Exception {
        mockMvc.perform(delete("/api/v1/recipe/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_SearchRecipeByCriteria_successfully() throws Exception {
        //create the recipe
        RecipeRequest createRecipeRequest = new RecipeRequest();
        createRecipeRequest.setName("pasta");
        createRecipeRequest.setType(RecipeType.VEGETARIAN);
        createRecipeRequest.setInstructions("ins");
        createRecipeRequest.setNumberOfServings(2);
        createRecipeRequest.setIngredients(List.of(101,102));
        MvcResult createdRecipe = mockMvc.perform(post("/api/v1/recipe")
                        .content(toJson(createRecipeRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        //prepare the search criteria and by newly created id
        Integer id = readByJsonPath(createdRecipe, "$.id");

        RecipeSearchRequest request = new RecipeSearchRequest();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFilterKey(FilterKeyEnum.NAME);
        searchCriteria.setOperation(OperationEnum.EQUALS);
        searchCriteria.setValue(String.valueOf(id));

        searchCriteriaList.add(searchCriteria);

        request.setDataOption(DataOptionEnum.ALL);
        request.setSearchCriteria(searchCriteriaList);

        mockMvc.perform(post("/api/v1/recipe/search")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void test_SearchRecipeByCriteria_fails() throws Exception {
        mockMvc.perform(post("/api/v1/recipe/search")
                .content(toJson(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }

}
