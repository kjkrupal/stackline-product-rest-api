package com.stackline.api.entity.request;

import java.util.List;

public class KeywordRequest {

    List<String> keywords;

    public KeywordRequest() {

    }

    public KeywordRequest(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
