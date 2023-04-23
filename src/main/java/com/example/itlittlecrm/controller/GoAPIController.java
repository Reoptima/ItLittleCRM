package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Products;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoAPIController {
    @GetMapping("/transfers")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String productsMain(Model model) {
        return "Bank/bank-main";
    }

    @GetMapping("/transfers/account")
    public String bankAccount() {
        return "Bank/bank-accounts";
    }
}