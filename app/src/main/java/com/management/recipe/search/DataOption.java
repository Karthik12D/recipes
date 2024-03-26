package com.management.recipe.search;

import java.util.Optional;

public enum DataOption {
    ANY, ALL;

    public static Optional<DataOption> getDataOption(final String dataOption) {
        String lowerDataOption = dataOption.toLowerCase();
        return switch (lowerDataOption) {
            case "all" -> Optional.of(ALL);
            case "any" -> Optional.of(ANY);
            default -> Optional.empty();
        };
    }
}
