package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Team;
import com.example.itlittlecrm.repo.TeamRepository;
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

    @GetMapping("/team")
    public String teamMain() {
        return "team/team-main";
    }

    @GetMapping("/team/add")
    public String teamAdd() {
        return "team/team-add";
    }

    @PostMapping("/team/add")
    public String teamPostAdd(@ModelAttribute("team") Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "team/team-add";
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
    public String teamView(@PathVariable(value = "id") long id, Model model) {
        if (teamDetails(id, model)) {
            return "redirect:/team";
        }
        return "team/team-view";
    }

    @GetMapping("/team/{id}/edit")
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

}
