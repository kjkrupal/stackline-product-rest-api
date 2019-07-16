package com.stackline.api.entity.request;

public class Pagination {

    int from;
    int size;


    public Pagination() {
    }

    public Pagination(int from, int size) {
        this.from = from;
        this.size = size;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
