package com.stackline.api.entity.response;

import java.util.ArrayList;
import java.util.List;

public class KeywordResponse {

    List<KeywordFrequency> keywordFrequencies;

    public KeywordResponse() {

    }

    public KeywordResponse(List<KeywordFrequency> keywordFrequencyList) {
        this.keywordFrequencies = keywordFrequencyList;
    }

    public List<KeywordFrequency> getKeywordFrequencyList() {
        return keywordFrequencies;
    }

    public void setKeywordFrequencyList(List<KeywordFrequency> keywordFrequencyList) {
        this.keywordFrequencies = keywordFrequencyList;
    }
}
