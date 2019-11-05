package com.kvart.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kvart.model.Product;
import com.kvart.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepo productRepo;

    @Async
    public CompletableFuture<String> getFilter(int page, String nameFilter) {

        LOGGER.info("Request to get a list product with filter");

        int to = page * 10;
        int from = to - 9;

        List<Product> productList = (List<Product>) productRepo.findAll();

        if (to > productList.size()) {
            to = productList.size();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(nameFilter, Pattern.CASE_INSENSITIVE);

        for (Product product: productList.subList(from, to)) {
            if (!pattern.matcher(product.getName()).matches()) {
                String json = gson.toJson(product);
                result.append(json);
            }
        }

        final String stringResult = result.toString();
        return CompletableFuture.completedFuture(stringResult);
    }
}
