package com.kvart.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kvart.model.Product;
import com.kvart.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepo productRepo;

    @Async
    public CompletableFuture<String> getFilter(String nameFilter) {

        LOGGER.info("Request to get a list product with filter");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile(nameFilter, Pattern.CASE_INSENSITIVE);

        for (Product product: productRepo.findAll()) {
            if (!pattern.matcher(product.getName()).matches()) {
                String json = gson.toJson(product);
                result.append(json);
            }
        }

        final String stringResult = result.toString();
        return CompletableFuture.completedFuture(stringResult);
    }
}
