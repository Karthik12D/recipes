package com.management.recipe.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchCriteria {
    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria(com.management.recipe.model.SearchCriteria request) {
        this.dataOption = request.getDataOption();
        this.filterKey = request.getFilterKey().name();
        this.operation = request.getOperation().name();
        this.value = request.getValue();
    }
}
