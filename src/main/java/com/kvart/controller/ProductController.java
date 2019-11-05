package com.kvart.controller;

import com.kvart.repo.ProductRepo;
import com.kvart.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Controller
@RequestMapping("/shop/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public @ResponseBody CompletableFuture<ResponseEntity> filters
            (@RequestParam(value = "page", required=true) Integer page,
             @RequestParam(value = "nameFilter", required=true) String nameFilter) {

        int value = (page * 10) - 9;

        if (productRepo.count() < value) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } else {
            return productService.getFilter(page, nameFilter).<ResponseEntity>thenApply(ResponseEntity::ok)
                    .exceptionally(handleGetProductFailure);
        }
    }

    private static Function<Throwable, ResponseEntity<? extends String>> handleGetProductFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

}
