package com.stackline.api.entity.response;

public class KeywordFrequency {

    String keyword;
    int count;

    public KeywordFrequency() {

    }

    public KeywordFrequency(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
