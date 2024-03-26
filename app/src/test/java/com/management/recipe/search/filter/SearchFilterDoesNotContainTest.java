package com.management.recipe.search.filter;

import com.management.recipe.search.SearchOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchFilterDoesNotContainTest {

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
        SearchFilterDoesNotContain filter  = new SearchFilterDoesNotContain();
        boolean b = filter.couldBeApplied(SearchOperation.NOT_EQUAL);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
        SearchFilterDoesNotContain filter  = new SearchFilterDoesNotContain();
        boolean b = filter.couldBeApplied(SearchOperation.EQUAL);
        Assertions.assertFalse(b);

    }

    @Test
    public void couldBeAppliedReturnsTrueWhenOperationIsDoesNotContain() {
        SearchFilterDoesNotContain filter  = new SearchFilterDoesNotContain();
        boolean b = filter.couldBeApplied(SearchOperation.DOES_NOT_CONTAIN);
        Assertions.assertTrue(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
        SearchFilterDoesNotContain filter  = new SearchFilterDoesNotContain();
        boolean b = filter.couldBeApplied(SearchOperation.CONTAINS);
        Assertions.assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
        SearchFilterDoesNotContain filter  = new SearchFilterDoesNotContain();
        boolean b = filter.couldBeApplied(null);
        Assertions.assertFalse(b);
    }

}