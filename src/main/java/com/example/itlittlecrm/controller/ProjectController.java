package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.*;
import com.example.itlittlecrm.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private SybsystemRepository sybsystemRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    @GetMapping("/project")
    @PreAuthorize("hasAnyAuthority('DEV','DEVLEAD','DEVOWN','ADMIN')")
    public String projectMain(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Team> teams = teamRepository.findByUsersContains(userRepository.findByUsername(username));
        List<Projects> projects = projectRepository.findByTeamsIn(teams);
        model.addAttribute("projects", projects);
        return "Project/project-main";
    }

    @GetMapping("/project/add")
    public String projectAdd(Model model) {
        return getString(model);
    }

    private String getString(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        Iterable<Projects> project = projectRepository.findAll();
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Reports> reports = reportsRepository.findAll();
        model.addAttribute("projects", project);
        model.addAttribute("project", new Projects());
        model.addAttribute("teams", teams);
        model.addAttribute("subsystems", subsystems);
        model.addAttribute("reports", reports);
        return "Project/project-add";
    }

    @PostMapping("/project/add")
    public String projectPostAdd(@ModelAttribute("project") @Valid Projects project, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        projectRepository.save(project);
        return "redirect:/project";
    }

    private boolean projectDetails(@PathVariable(value = "id") long id, Model model) {
        if (!projectRepository.existsById(id)) {
            return true;
        }
        Projects projects = projectRepository.findById(id).orElseThrow();
        model.addAttribute("projects", projects);
        return false;
    }

    @GetMapping("/project/{id}")
    public String projectDetail(@PathVariable(value = "id") long id, Model model) {
        if (projectDetails(id, model)) {
            return "redirect:/project";
        }
        return "Project/project-details";
    }

    @GetMapping("/project/{projects}/edit")
    public String projectEdit(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Reports> reports = reportsRepository.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("subsystems", subsystems);
        model.addAttribute("reports", reports);
        return "Project/project-edit";
    }

    @PostMapping("/project/{projects}/edit")
    public String projectPostUpdate(@ModelAttribute("projects")
                                    @Valid Projects projects, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Project/project-edit";
        }
        projectRepository.save(projects);
        return "redirect:/project";
    }

    @PostMapping("/project/{id}/remove")
    public String projectPostDelete(@PathVariable(value = "id") long id) {
        Projects project = projectRepository.findById(id).orElseThrow();
        projectRepository.delete(project);
        return "redirect:/project";
    }

    @GetMapping("/project/filter")
    public String projectFilter() {
        return "Project/project-filter";
    }

    @PostMapping("/project/filter/result")
    public String projectFilterResult(@RequestParam String title, Model model) {
        List<Projects> result = projectRepository.findByProjectNameContains(title);
        model.addAttribute("result", result);
        return "Project/project-filter";
    }
}