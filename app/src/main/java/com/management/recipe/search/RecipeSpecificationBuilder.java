package com.management.recipe.search;

import com.management.recipe.models.Recipe;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public class RecipeSpecificationBuilder {
    private final List<SearchCriteria> params;

    public RecipeSpecificationBuilder(List<SearchCriteria> searchCriterionRequests) {
        this.params = searchCriterionRequests;
    }

    public final RecipeSpecificationBuilder with(SearchCriteria searchCriteriaRequest) {
        params.add(searchCriteriaRequest);
        return this;
    }

    public Optional<Specification<Recipe>> build() {
        if (params.size() == 0) return Optional.empty();

        Specification<Recipe> result = new RecipeSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteria = params.get(i);
            Optional<DataOption> dataOption = DataOption.getDataOption(criteria.getDataOption());
            if (dataOption.isPresent()) {
                result = (dataOption.get() == DataOption.ALL)
                        ? Specification.where(result).and(new RecipeSpecification(criteria))
                        : Specification.where(result).or(new RecipeSpecification(criteria));
            }
        }
        return Optional.of(result);
    }
}
