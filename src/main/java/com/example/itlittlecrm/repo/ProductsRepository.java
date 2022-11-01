package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Long> {
    List<Products> findByProductNameContains(String productName);

    Products findByProductName(String productName);
}
