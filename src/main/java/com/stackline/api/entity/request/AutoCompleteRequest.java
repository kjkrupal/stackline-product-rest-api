package com.stackline.api.entity.request;

public class AutoCompleteRequest {

    String type;
    String prefix;

    public AutoCompleteRequest(String type, String prefix) {
        this.type = type;
        this.prefix = prefix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
