package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.*;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.ReportsRepository;
import com.example.itlittlecrm.repo.SybsystemRepository;
import com.example.itlittlecrm.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SybsystemRepository sybsystemRepository;

    @Autowired
    private ReportsRepository reportsRepository;

    @GetMapping("/project")
    public String projectMain(Model model) {
        Iterable<Projects> projects = projectRepository.findAll();
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
        model.addAttribute("project" , new Projects());
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
        Iterable<Projects> projects = projectRepository.findAll();
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

    @GetMapping("/project/{project}/edit")
    public String projectEdit(Model model, Projects project) {
        Iterable<Team> teams = teamRepository.findAll();
        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
        Iterable<Reports> reports = reportsRepository.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("subsystems", subsystems);
        model.addAttribute("reports", reports);
        return "Project/project-edit";
    }

    @PostMapping("/project/{id}/edit")
    public String projectPostUpdate(@ModelAttribute("project") @Valid Projects project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Project/project-edit";
        }
        projectRepository.save(project);
        return "redirect:/project";
    }

    @PostMapping("/project/{id}/remove")
    public String projectPostDelete(@PathVariable(value = "id") long id, Model model) {
        Projects project = projectRepository.findById(id).orElseThrow();
        projectRepository.delete(project);
        return "redirect:/project";
    }
}
