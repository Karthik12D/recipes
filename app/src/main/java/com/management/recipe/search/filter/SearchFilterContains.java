package com.management.recipe.search.filter;


import com.management.recipe.models.Recipe;
import com.management.recipe.search.SearchOperation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.*;

import static com.management.recipe.config.DatabaseAttributes.INGREDIENT_KEY;

public class SearchFilterContains implements SearchFilter {

    @Override
    public boolean couldBeApplied(SearchOperation opt) {
        return opt == SearchOperation.CONTAINS;
    }

    @Override
    public Predicate apply(CriteriaBuilder cb, String filterKey, String filterValue, Root<Recipe> root, Join<Object, Object> subRoot) {
        if (filterKey.equals(INGREDIENT_KEY))
            return cb.like(cb.lower(subRoot.get(filterKey).as(String.class)), "%" + filterValue + "%");

        return cb.like(cb.lower(root.get(filterKey).as(String.class)), "%" + filterValue + "%");
    }
}
