package com.stackline.api.entity.request;

import java.util.List;

public class SearchRequest {

    List<Condition> conditions;
    Pagination pagination;


    public SearchRequest(List<Condition> conditions, Pagination pagination) {
        this.conditions = conditions;
        this.pagination = pagination;
    }

    public List<Condition> getCondition() {
        return conditions;
    }

    public void setCondition(List<Condition> condition) {
        this.conditions = condition;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
