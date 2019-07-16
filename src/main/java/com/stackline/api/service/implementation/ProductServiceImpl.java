package com.stackline.api.service.implementation;

import com.stackline.api.entity.request.Condition;
import com.stackline.api.entity.request.Pagination;
import com.stackline.api.entity.response.KeywordFrequency;
import com.stackline.api.entity.response.KeywordResponse;
import com.stackline.api.entity.response.Product;
import com.stackline.api.service.ProductService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<String> autoComplete(String type, String prefix) throws FileNotFoundException, IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/data.tsv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> list = bufferedReader
                .lines()
                .map(input -> {
                    if(type.equals("name"))
                        return input.split("\t")[1];
                    if(type.equals("brand"))
                        return input.split("\t")[3];
                    if(type.equals("category"))
                        return input.split("\t")[5];
                    return "";
                })
                .filter(input -> {

                    if(input.length() >= prefix.length() && prefix.toLowerCase().equals(input.toLowerCase().substring(0, prefix.length()))) {
                        return true;
                    }
                    return false;
                })
                .distinct()
                .collect(Collectors.toList());

        bufferedReader.close();
        inputStream.close();

        return list;
    }

    @Override
    public List<Product> searchProducts(List<Condition> conditionList, Pagination pagination) throws FileNotFoundException, IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/data.tsv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<Product> list = bufferedReader
                .lines()
                .filter(line -> {
                    String[] data = line.split("\t");
                    for(Condition condition : conditionList) {
                        String type = condition.getType();
                        List<String> values = condition.getValues();
                        boolean validBrand = false;
                        boolean validCategory = false;
                        if(type.equals("brandName")) {
                            for(String brandName : values) {
                                if(brandName.equals(data[3])) {
                                    validBrand = true;
                                    break;
                                }
                            }
                            if(!validBrand)
                                return false;
                        }
                        if(type.equals("categoryName")) {
                            for(String categoryName : values) {
                                if(!categoryName.equals(data[5])) {
                                    validCategory = true;
                                    break;
                                }
                            }
                            if(!validCategory)
                                return false;
                        }
                    }
                    return true;
                })
                .map(line -> {
                    String[] data = line.split("\t");
                    Product product = new Product();
                    product.setProductId(data[0]);
                    product.setTitle(data[1]);
                    product.setBrandId(data[2]);
                    product.setBrandName(data[3]);
                    product.setCategoryId(data[4]);
                    product.setCategoryName(data[5]);
                    return product;
                })
                .collect(Collectors.toList());

        bufferedReader.close();
        inputStream.close();

        return list;
    }

    @Override
    public List<Product> paginate(List<Product> allProducts, int begin, int total, int size) {
        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            if(begin > total - 1)
                break;
            productList.add(allProducts.get(begin));
            begin++;
        }
        return productList;
    }

    @Override
    public KeywordResponse countKeywords(List<String> keywords) throws FileNotFoundException, IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/data.tsv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> sentenceList = bufferedReader
                .lines()
                .map(input ->  input.split("\t")[1])
                .collect(Collectors.toList());

        List<KeywordFrequency> frequencyList = new ArrayList<>();
        KeywordResponse keywordResponse = new KeywordResponse();
        keywordResponse.setKeywordFrequencyList(frequencyList);

        for(String keyword : keywords) {

            KeywordFrequency keywordFrequency = new KeywordFrequency();
            keywordFrequency.setKeyword(keyword);
            int currentCount = 0;

            for(String sentence : sentenceList) {
                String[] splittedSentence = sentence.split(" ");
                for(String word : splittedSentence) {
                    if(word.equals(keyword)) {
                        currentCount++;
                    }
                }
            }

            keywordFrequency.setCount(currentCount);
            keywordResponse.getKeywordFrequencyList().add(keywordFrequency);

        }

        bufferedReader.close();
        inputStream.close();

        return keywordResponse;
    }


}
