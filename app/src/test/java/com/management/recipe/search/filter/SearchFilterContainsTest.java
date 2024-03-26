package com.management.recipe.search.filter;

import com.management.recipe.search.SearchOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchFilterContainsTest {

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.NOT_EQUAL);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.EQUAL);
        Assertions.assertFalse(b);

    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.DOES_NOT_CONTAIN);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsTrueWhenOperationIsContain() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.CONTAINS);
        Assertions.assertTrue(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(null);
        Assertions.assertFalse(b);
    }

}