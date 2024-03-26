package com.management.recipe.search.filter;

import com.management.recipe.search.SearchOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SearchFilterNotEqualTest {

    @Test
    public void couldBeAppliedReturnsTrueWhenOperationIsNotEqual() {
        SearchFilterNotEqual filterNotEqual  = new SearchFilterNotEqual();
        boolean b = filterNotEqual.couldBeApplied(SearchOperation.NOT_EQUAL);
        Assertions.assertTrue(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
        SearchFilterNotEqual filterNotEqual  = new SearchFilterNotEqual();
        boolean b = filterNotEqual.couldBeApplied(SearchOperation.EQUAL);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
        SearchFilterNotEqual filterNotEqual  = new SearchFilterNotEqual();
        boolean b = filterNotEqual.couldBeApplied(SearchOperation.DOES_NOT_CONTAIN);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
        SearchFilterNotEqual filterNotEqual  = new SearchFilterNotEqual();
        boolean b = filterNotEqual.couldBeApplied(SearchOperation.CONTAINS);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
        SearchFilterNotEqual filterNotEqual  = new SearchFilterNotEqual();
        boolean b = filterNotEqual.couldBeApplied(null);
        Assertions.assertFalse(b);
    }

    @Test
    public void apply() {
    }
}