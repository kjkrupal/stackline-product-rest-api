package com.stackline.api.service;

import com.stackline.api.entity.request.Condition;
import com.stackline.api.entity.request.Pagination;
import com.stackline.api.entity.response.KeywordResponse;
import com.stackline.api.entity.response.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<String> autoComplete(String type, String prefix) throws FileNotFoundException, IOException;

    List<Product> searchProducts(List<Condition> conditionList, Pagination pagination) throws FileNotFoundException, IOException;

    List<Product> paginate(List<Product> allProducts, int begin, int total, int size);

    KeywordResponse countKeywords(List<String> keywords) throws FileNotFoundException, IOException;

}
