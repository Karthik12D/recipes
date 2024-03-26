package com.management.recipe.search.filter;

import com.management.recipe.search.SearchOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SearchFilterEqualTest {

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
        SearchFilterEqual filterEqual  = new SearchFilterEqual();
        boolean b = filterEqual.couldBeApplied(SearchOperation.NOT_EQUAL);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsTrueWhenOperationIsEqual() {
        SearchFilterEqual filterEqual  = new SearchFilterEqual();
        boolean b = filterEqual.couldBeApplied(SearchOperation.EQUAL);
        Assertions.assertTrue(b);

    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
        SearchFilterEqual filterEqual  = new SearchFilterEqual();
        boolean b = filterEqual.couldBeApplied(SearchOperation.DOES_NOT_CONTAIN);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
        SearchFilterEqual filterEqual  = new SearchFilterEqual();
        boolean b = filterEqual.couldBeApplied(SearchOperation.CONTAINS);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
        SearchFilterEqual filterEqual  = new SearchFilterEqual();
        boolean b = filterEqual.couldBeApplied(null);
        Assertions.assertFalse(b);
    }

    @Test
    public void apply() {
    }
}