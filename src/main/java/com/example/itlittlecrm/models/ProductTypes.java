package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProductTypes {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    private Set<Products> products;

    private String productTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}
