package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Team;
import com.example.itlittlecrm.models.User;
import com.example.itlittlecrm.repo.TeamRepository;
import com.example.itlittlecrm.repo.UserRepository;
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
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/team")

    public String teamMain(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("teams", teams);
        return "Team/team-main";
    }

    @GetMapping("/team/add")
    public String teamAdd(Team team, Model model) {
        return getString(model);
    }

    private String getString(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("users", users);
        return "team/team-add";
    }

    @PostMapping("/team/add")
    public String teamPostAdd(@ModelAttribute("team") @Valid Team team, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        teamRepository.save(team);
        return "redirect:/team";
    }

    private Boolean teamDetails(@PathVariable(value = "id") long id, Model model) {
        if (!teamRepository.existsById(id)) {
            return true;
        }
        Team team = teamRepository.findById(id).orElseThrow();
        model.addAttribute("team", team);
        return false;
    }

    @GetMapping("/team/{id}")
    public String teamDetail(@PathVariable(value = "id") long id, Model model) {
        if (teamDetails(id, model)) {
            return "redirect:/team";
        }
        return "Team/team-details";
    }

    @GetMapping("/team/{team}/edit")
    public String teamEdit(@PathVariable(value = "id") long id, Model model) {
        if (teamDetails(id, model)) {
            return "redirect:/team";
        }
        return "team/team-edit";
    }

    @PostMapping("/team/{id}/edit")
    public String teamPostEdit(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "team/team-edit";
        }
        teamRepository.save(team);
        return "redirect:/team";
    }

    @PostMapping("/team/{id}/remove")
    public String teamPostDelete(@PathVariable(value = "id") long id, Model model) {
        Team team = teamRepository.findById(id).orElseThrow();
        teamRepository.delete(team);
        return "redirect:/team";
    }
}
