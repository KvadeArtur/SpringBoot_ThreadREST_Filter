package com.kvart.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kvart.repo.ProductRepo;
import com.kvart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Pattern;

@Controller
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping(value = "/shop/product")
    public ResponseEntity<String> filters(@RequestParam(value = "nameFilter") String nameFilter) {

        List<Product> products = (List<Product>) productRepo.findAll();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder result = new StringBuilder();

        try {
            Pattern pattern = Pattern.compile(nameFilter, Pattern.CASE_INSENSITIVE);

            for (Product product: products) {
                if (!pattern.matcher(product.getName()).matches()) {
                    String json = gson.toJson(product);
                    result.append(json);
                }
            }

            return new ResponseEntity<>(result.toString(), HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
