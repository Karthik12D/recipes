package com.management.recipe.search;

import java.util.Optional;

public enum SearchOperation {

    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL;


    public static Optional<SearchOperation> getOperation(final String input) {
        String lowerInput = input.toLowerCase();
        return switch (lowerInput) {
            case "cn"-> Optional.of(CONTAINS);
            case "nc" -> Optional.of(DOES_NOT_CONTAIN);
            case "eq" -> Optional.of(EQUAL);
            case "ne" -> Optional.of(NOT_EQUAL);
            default -> Optional.empty();
        };
    }
}
