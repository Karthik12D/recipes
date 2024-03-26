package com.management.recipe.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class SearchOperationTest {
    @Test
    public void simpleEnumExampleInsideClassTest() {
        SearchOperation contains = SearchOperation.CONTAINS;
        SearchOperation doesNotContain = SearchOperation.DOES_NOT_CONTAIN;
        SearchOperation equal = SearchOperation.EQUAL;
        SearchOperation notEqual = SearchOperation.NOT_EQUAL;
        Assertions.assertEquals(SearchOperation.valueOf("CONTAINS"), contains);
        Assertions.assertEquals(SearchOperation.valueOf("DOES_NOT_CONTAIN"), doesNotContain);
        Assertions.assertEquals(SearchOperation.valueOf("EQUAL"), equal);
        Assertions.assertEquals(SearchOperation.valueOf("NOT_EQUAL"), notEqual);
    }

    @Test
    public void whenInputEnterItReturnsCorrespondingEnum() {
        Optional<SearchOperation> cn = SearchOperation.getOperation("cn");
        Optional<SearchOperation> nc = SearchOperation.getOperation("nc");
        Optional<SearchOperation> eq = SearchOperation.getOperation("eq");
        Optional<SearchOperation> ne = SearchOperation.getOperation("ne");
        Assertions.assertTrue(cn.isPresent());
        Assertions.assertTrue(nc.isPresent());
        Assertions.assertTrue(eq.isPresent());
        Assertions.assertTrue(ne.isPresent());
        Assertions.assertEquals(SearchOperation.CONTAINS, cn.get());
        Assertions.assertEquals(SearchOperation.DOES_NOT_CONTAIN, nc.get());
        Assertions.assertEquals(SearchOperation.EQUAL, eq.get());
        Assertions.assertEquals(SearchOperation.NOT_EQUAL, ne.get());
    }
}
