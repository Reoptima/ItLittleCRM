package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.models.Team;
import com.example.itlittlecrm.models.User;
import com.example.itlittlecrm.repo.ProductsRepository;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.SybsystemRepository;
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
public class SubsystemController {
    @Autowired
    SybsystemRepository sybsystemRepository;
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/subsystem")
    public String subsystemMain(Model model) {
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Projects> projects = projectRepository.findAll();
        model.addAttribute("Subsystem", subsystems);
        model.addAttribute("projects", projects);
        return "Subsystem/subsystem-main";
    }

    @GetMapping("/subsystem/add")
    public String subsystemAdd(Subsystem subsystem, Model model) {
        return getString(model);
    }

    private String getString(Model model) {
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Projects> projects = projectRepository.findAll();
        model.addAttribute("Subsystem", subsystems);
        model.addAttribute("projects", projects);
        return "Subsystem/subsystem-add";
    }

    @PostMapping("/subsystem/add")
    public String subsystemPostAdd(@ModelAttribute("Subsystem") Subsystem subsystem, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        sybsystemRepository.save(subsystem);
        return "redirect:/subsystem";
    }

    @GetMapping("/subsystem/{id}/edit")
    private String projectDetails(@PathVariable(value = "id") long id, Model model) {
        if (!sybsystemRepository.existsById(id)) {
            return "redirect:/subsystem";
        }
        Subsystem subsystem = sybsystemRepository.findById(id).orElseThrow();
        model.addAttribute("Subsystem", subsystem);
        return "Subsystem/subsystem-edit";
    }

    @PostMapping("/subsystem/{subsystem}/edit")
    public String subsystemPostUpdate(@ModelAttribute("Subsystem") @Valid Subsystem subsystem, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Subsystem/subsystem-edit";
        }
        sybsystemRepository.save(subsystem);
        return "redirect:/subsystem";
    }

    @PostMapping("/subsystem/{id}/remove")
    public String subsystemPostDelete(@PathVariable(value = "id") long id, Model model) {
        Subsystem subsystem = sybsystemRepository.findById(id).orElseThrow();
        sybsystemRepository.delete(subsystem);
        return "redirect:/subsystem";
    }

}
