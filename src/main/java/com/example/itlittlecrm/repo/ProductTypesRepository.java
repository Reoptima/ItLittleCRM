package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.ProductTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductTypesRepository extends CrudRepository<ProductTypes, Long> {
    List<ProductTypes> findByProductTypeNameContains(String productTypeName);
    ProductTypes findByProductTypeName(String productTypeName);

    List<ProductTypes> findByProductsContains(ProductTypes productTypes);
}

