package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Client;
import com.example.itlittlecrm.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/client")
    public String clientMain(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "Client/client-main";
    }

    @GetMapping("/client/add")
    public String clientAdd(Model model) {
        return getString(model);
    }

    @PostMapping("/client/add")
    public String clientPostAdd(@ModelAttribute("client") @Valid Client client, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("errorMessage", errorMessage);
            return getString(model);
        }

        clientRepository.save(client);
        return "redirect:/client";
    }

    private String getString(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("client", new Client());
        return "Client/client-add";
    }

    private boolean clientDetails(@PathVariable(value = "id") long id, Model model) {
        if (!clientRepository.existsById(id)) {
            return true;
        }
        Client client = clientRepository.findById(id).orElseThrow();
        model.addAttribute("client", client);
        return false;
    }

    @GetMapping("/client/{id}")
    public String clientDetail(@PathVariable(value = "id") long id, Model model) {
        if (clientDetails(id, model)) {
            return "redirect:/client";
        }
        return "Client/client-details";
    }

    @GetMapping("/client/{client}/edit")
    public String clientEdit(Model model, Client client) {
        return "Client/client-edit";
    }

    @PostMapping("/client/{id}/edit")
    public String clientPostEdit(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Client/client-edit";
        }
        clientRepository.save(client);
        return "redirect:/client";
    }

    @PostMapping("/client/{id}/remove")
    public String clientPostDelete(@PathVariable(value = "id") long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);
        return "redirect:/client";
    }

}
