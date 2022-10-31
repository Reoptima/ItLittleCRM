package com.example.itlittlecrm.controller;


import com.example.itlittlecrm.models.Team;
import com.example.itlittlecrm.models.User;
import com.example.itlittlecrm.repo.TeamRepository;
import com.example.itlittlecrm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class UserContoroller {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/user")
    public String userMain(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("User", users);
        return "User/user-main";
    }

    @GetMapping("/user/add")
    public String userAdd(User user, Model model) {
        return getString(model);
    }

    @PostMapping("/user/add")
    public String userPostAdd(@ModelAttribute("user") @Valid User user, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    private String getString(Model model) {
        Iterable<User> users = userRepository.findAll();
        Iterable<Team> teams = teamRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("teams", teams);
        return "user/user-add";
    }

    @GetMapping("/user/{id}")
    public String userDetail(@PathVariable(value = "id") long id, Model model) {
        if (userDetails(id, model)) {
            return "redirect:/user";
        }
        return "User/user-details";
    }


    public boolean userDetails(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return true;
        }
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return false;
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id, Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);
        return "User/user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String userPostUpdate(@PathVariable(value = "id") long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Iterable<Team> teams = teamRepository.findAll();
            model.addAttribute("teams", teams);
            return "User/user-edit";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user/{id}/remove")
    public String userPostDelete(@PathVariable(value = "id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/user";
    }
}
