package com.management.recipe.search;

import com.management.recipe.models.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeSpecificationBuilderTest {
    List<SearchCriteria> params;

    public RecipeSpecificationBuilderTest() {
        params = new ArrayList<>();
    }

    @Test
    public void test_buildMethodWhenParamsIsEmpty_successfully() {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(params);
        Optional<Specification<Recipe>> build = builder.build();
        Assertions.assertEquals(Optional.empty(), build);
    }

    @Test
    public void test_buildMethodWhenParamsIsNotEmpty_successfully() {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(params);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setDataOption("all");
        searchCriteria.setFilterKey("name");
        searchCriteria.setOperation("cn");
        searchCriteria.setValue("pasta");
        params.add(searchCriteria);
        Optional<Specification<Recipe>> build = builder.build();
        Assertions.assertTrue(build.isPresent());
     }

}
