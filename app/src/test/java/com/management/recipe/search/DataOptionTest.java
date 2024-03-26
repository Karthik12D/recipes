package com.management.recipe.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DataOptionTest {

    @Test
    public void simpleEnumExampleInsideClassTest() {
        DataOption option1 = DataOption.ALL;
        DataOption option2 = DataOption.ANY;
        Assertions.assertEquals(DataOption.valueOf("ALL"), option1);
        Assertions.assertEquals(DataOption.valueOf("ANY"), option2);
    }

    @Test
    public void whenInputEnterItReturnsCorrespondingEnum() {
        Optional<DataOption> all = DataOption.getDataOption("all");
        Optional<DataOption> any = DataOption.getDataOption("any");
        Assertions.assertTrue(all.isPresent());
        Assertions.assertTrue(any.isPresent());
        Assertions.assertEquals(DataOption.ALL, all.get());
        Assertions.assertEquals(DataOption.ANY, any.get());
    }
}
