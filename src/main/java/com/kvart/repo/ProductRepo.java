package com.kvart.repo;

import com.kvart.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, String> {

}
