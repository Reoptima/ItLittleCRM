package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.ProductTypes;
import com.example.itlittlecrm.models.Products;
import com.example.itlittlecrm.repo.ProductTypesRepository;
import com.example.itlittlecrm.repo.ProductsRepository;
import com.example.itlittlecrm.repo.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductsController {
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    ProductTypesRepository productTypesRepository;

    @GetMapping("/products")
    @PreAuthorize("hasAnyAuthority('PRODUCT','ADMIN')")
    public String productsMain(Model model) {
        Iterable<Products> products = productsRepository.findAll();
        model.addAttribute("products", products);
        return "Products/products-main";
    }

    @GetMapping("/products/add")
    public String productsAdd(Model model, Products products, ProductTypes productTypes) {
        return getString(model, products, productTypes);
    }

    private String getString(Model model, Products products, ProductTypes productTypes) {
        products.setProductType(productTypes);
        Iterable<Products> productsIterable = productsRepository.findAll();
        Iterable<ProductTypes> productTypesIterable = productTypesRepository.findAll();
        model.addAttribute("productTypesIterable", productTypesIterable);
        model.addAttribute("products", productsIterable);
        model.addAttribute("products", products);
        return "Products/products-add";
    }

    @PostMapping("/products/add")
    public String productsPostAdd(@ModelAttribute("Products") Products products, ProductTypes productTypes, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, products, productTypes);
        }
        productsRepository.save(products);
        return "redirect:/products";
    }

    @GetMapping("/products/{products}")
    public String productsDetail(Products products) {
        return "Products/products-detail";
    }

    @GetMapping("/products/{products}/edit")
    private String productEdit(Products products, Model model) {
        Iterable<ProductTypes> productTypesIterable = productTypesRepository.findAll();
        model.addAttribute("productTypesIterable", productTypesIterable);
        return "Products/products-edit";
    }

    @PostMapping("/products/{id}/edit")
    public String productPostEdit(@ModelAttribute("products") @Valid Products products, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Products/products-edit";
        }
        productsRepository.save(products);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}/remove")
    public String productPostRemove(Products products) {
        productsRepository.delete(products);
        return "redirect:/products";
    }
}