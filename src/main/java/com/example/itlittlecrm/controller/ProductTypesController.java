package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.ProductTypes;
import com.example.itlittlecrm.repo.ProductTypesRepository;
import com.example.itlittlecrm.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductTypesController {
    @Autowired
    ProductTypesRepository productTypesRepository;
    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/types")
    @PreAuthorize("hasAnyAuthority('PRODUCT','ADMIN')")
    public String typesMain(Model model) {
        Iterable<ProductTypes> productTypes = productTypesRepository.findAll();
        model.addAttribute("productTypes", productTypes);
        return "ProductTypes/types-main";
    }

    @GetMapping("/types/add")
    public String typesAdd(Model model, ProductTypes productTypes) {
        return getString(model, productTypes);
    }

    private String getString(Model model, ProductTypes productTypes) {
        model.addAttribute("productTypes", productTypes);
        return "ProductTypes/types-add";
    }

    @PostMapping("/types/add")
    public String typesPostAdd(@ModelAttribute("ProductTypes") ProductTypes productTypes, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, productTypes);
        }
        productTypesRepository.save(productTypes);
        return "redirect:/types";
    }

    @GetMapping("/types/{productTypes}")
    public String typesDetail(ProductTypes productTypes, Model model) {
        return "ProductTypes/types-detail";
    }
    @GetMapping("/types/{productTypes}/edit")
    public String typesEdit(ProductTypes productTypes, Model model) {
        return "ProductTypes/types-edit";
    }

    @PostMapping("/types/{id}/edit")
    public String typesPostUpdate(@ModelAttribute("ProductTypes") ProductTypes productTypes, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ProductTypes/types-edit";
        }
        productTypesRepository.save(productTypes);
        return "redirect:/types";
    }

    @PostMapping("/types/{id}/remove")
    public String typesPostDelete(@ModelAttribute("ProductTypes") ProductTypes productTypes) {
        productTypesRepository.delete(productTypes);
        return "redirect:/types";
    }
}
