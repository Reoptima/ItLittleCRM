package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.*;
import com.example.itlittlecrm.repo.ClientRepository;
import com.example.itlittlecrm.repo.ProductsRepository;
import com.example.itlittlecrm.repo.SalesRepository;
import com.example.itlittlecrm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SalesController {
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/sales")
    public String salesMain(Model model) {
        Iterable<Sales> sales = salesRepository.findAll();
        model.addAttribute("sales", sales);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productsRepository.findAll());
        return "Sales/sales-main";
    }

    @GetMapping("/sales/add")
    public String SalesAdd(Model model) {
        return getString(model);
    }

    private String getString(Model model) {
        Iterable<Sales> sale = salesRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        Iterable<Products> products = productsRepository.findAll();
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("sales", sale);
        model.addAttribute("sale", new Sales());
        model.addAttribute("users", users);
        model.addAttribute("products", products);
        return "Sales/sales-add";
    }

    @PostMapping("/sales/add")
    public String SalesPostAdd(@ModelAttribute("sale") Sales sales, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        salesRepository.save(sales);
        return "redirect:/sales";
    }

    private boolean salesDetails(@PathVariable(value = "id") long id, Model model) {
        if (!salesRepository.existsById(id)) {
            return true;
        }
        Iterable<Client> clients = clientRepository.findAll();
        Sales sales = salesRepository.findById(id).orElseThrow();
        model.addAttribute("sales", sales);
        model.addAttribute("clients", clients);
        return false;
    }

    @GetMapping("/sales/{id}")
    public String salesDetail(@PathVariable(value = "id") long id, Model model) {
        if (salesDetails(id, model)) {
            return "redirect:/sales";
        }
        return "Sales/sales-details";
    }
    @GetMapping("/sales/{sales}/edit")
    public String salesEdit(Model model) {
        Iterable<Sales> sale = salesRepository.findAll();
        Iterable<Products> products = productsRepository.findAll();
        Iterable<Client> clients = clientRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("sales", sale);
        model.addAttribute("sale", new Sales());
        model.addAttribute("products", products);
        model.addAttribute("clients", clients);
        model.addAttribute("users", users);
        return "Sales/sales-edit";
    }

    @PostMapping("/sales/{id}/edit")
    public String salesPostUpdate(@ModelAttribute("sales") Sales sales, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Team/team-edit";
        }
        salesRepository.save(sales);
        return "redirect:/sales";
    }

    @PostMapping("/sales/{id}/remove")
    public String salesPostDelete(@PathVariable(value = "id") long id, Model model) {
        Sales sales = salesRepository.findById(id).orElseThrow();
        salesRepository.delete(sales);
        return "redirect:/sales";
    }
}
