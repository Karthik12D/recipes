package com.management.recipe.search.filter;

import com.management.recipe.models.Recipe;
import com.management.recipe.search.SearchOperation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface SearchFilter  {
    boolean couldBeApplied(SearchOperation opt);
    Predicate apply(CriteriaBuilder cb, String filterKey, String filterValue, Root<Recipe> root, Join<Object, Object> subRoot);
}