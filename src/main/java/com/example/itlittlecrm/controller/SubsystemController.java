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
import org.springframework.web.bind.annotation.GetMapping;

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
        Iterable<Projects> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "Subsystem/subsystem-add";
    }
    private String getString(Model model) {
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Projects> projects = projectRepository.findAll();
        model.addAttribute("Subsystem", subsystems);
        model.addAttribute("projects", projects);
        return "Subsystem/subsystem-add";
    }
}
