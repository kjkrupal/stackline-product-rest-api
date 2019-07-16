package com.stackline.api.controller;

import com.stackline.api.entity.APIError;
import com.stackline.api.entity.request.*;
import com.stackline.api.entity.response.KeywordResponse;
import com.stackline.api.entity.response.Product;
import com.stackline.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/autocomplete", method = RequestMethod.POST)
    public ResponseEntity<Object> autoComplete(@RequestBody AutoCompleteRequest autoCompleteRequest) throws FileNotFoundException, IOException {

        String type = autoCompleteRequest.getType();
        String prefix = autoCompleteRequest.getPrefix();

        if(type == null || prefix == null) {
            return new ResponseEntity<>(new APIError("Must provide a type and prefix value"), HttpStatus.BAD_REQUEST);
        }

        if(!(type.equals("brand") || type.equals("category") || type.equals("name"))) {
            return new ResponseEntity<>(new APIError("Type should be category, brand or name"), HttpStatus.BAD_REQUEST);
        }

        List<String> autocompleteList = productService.autoComplete(type, prefix);

        return new ResponseEntity<>(autocompleteList, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<Object> search(@RequestBody SearchRequest searchRequest) throws FileNotFoundException, IOException {

        List<Condition> conditionList = searchRequest.getCondition();
        Pagination pagination = searchRequest.getPagination();

        int size = pagination.getSize();
        int from = pagination.getFrom();

        if(conditionList == null) {
            return new ResponseEntity<>(new APIError("Must provide proper conditions"), HttpStatus.BAD_REQUEST);
        }

        if(pagination == null) {
            return new ResponseEntity<>(new APIError("Must provide proper pagination"), HttpStatus.BAD_REQUEST);
        }

        if(size <= 0) {
            return new ResponseEntity<>(new APIError("Size should be greater than 0"), HttpStatus.BAD_REQUEST);
        }

        if(from <= 0) {
            return new ResponseEntity<>(new APIError("From should be greater than 0"), HttpStatus.BAD_REQUEST);
        }

        List<Product> allProducts = productService.searchProducts(conditionList, pagination);

        int total = allProducts.size();
        int begin = size * (from - 1);

        if(begin > total - 1) {
            return new ResponseEntity<>(new APIError("Page value is too high"), HttpStatus.NOT_FOUND);
        }

        List<Product> productList = productService.paginate(allProducts, begin, total, size);

        return new ResponseEntity<>(productList, HttpStatus.OK);

    }

    @RequestMapping(value = "/keywords", method = RequestMethod.POST)
    public ResponseEntity<Object> countKeywords(@RequestBody KeywordRequest keywordRequest) throws FileNotFoundException, IOException {

        List<String> keywords = keywordRequest.getKeywords();

        if(keywords == null) {
            return new ResponseEntity<>(new APIError("Must provide a list of keywords"), HttpStatus.BAD_REQUEST);
        }

        if(keywords.size() == 0) {
            return new ResponseEntity<>(new APIError("Keywords list is empty"), HttpStatus.BAD_REQUEST);
        }

        KeywordResponse keywordResponse = productService.countKeywords(keywords);

        return new ResponseEntity<>(keywordResponse, HttpStatus.OK);

    }

}
