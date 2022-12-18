package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.SybsystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SubsystemController {
    @Autowired
    SybsystemRepository sybsystemRepository;
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/project/{project}/subsystems")
    public String viewSubsystems(@PathVariable Projects projects, Model model) {
        Projects project = projectRepository.findById(projects.getId()).orElse(null);

        assert project != null;
        List<Subsystem> subsystems = project.getSubsystems();
        model.addAttribute("project", project);
        model.addAttribute("subsystems", subsystems);
        return "Subsystem/subsystem-main";
    }

    @GetMapping("/project/{projects}/subsystem/add")
    public String subsystemAdd(Subsystem subsystem, Projects projects, @RequestParam("redirect") String redirect, Model model) {
        return getString(model, subsystem, projects, redirect);
    }

    private String getString(Model model, Subsystem subsystem, Projects projects, String redirect) {
        subsystem.setProjects(projects);
        model.addAttribute("redirect", redirect);
        return "Subsystem/subsystem-add";
//        Iterable<Subsystem> subsystems = sybsystemRepository.findAll();
//        Iterable<Projects> projects = projectRepository.findAll();
//        model.addAttribute("Subsystem", subsystems);
//        model.addAttribute("projects", projects);

    }

    @PostMapping("/project/{projects}/subsystem/add")
    public String subsystemPostAdd(@ModelAttribute("Subsystem") Subsystem subsystem, Projects projects, Model model, String redirect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, subsystem, projects, redirect);
        }
        sybsystemRepository.save(subsystem);
        return "redirect:" + redirect;
    }

//        if (bindingResult.hasErrors()) {
//            return getString(model, subsystem, projects, redirect);
//        }
//        Projects project = projectRepository.findById(projectId).orElse(null);
//        if (project == null) {
//            return getString(model);
//        }

    @GetMapping("/subsystem/{subsystem}")
    public String subsystemDetail(Subsystem subsystem) {
        return "Subsystem/subsystem-details";
    }

    @GetMapping("/subsystem/{subsystem}/edit")
    private String subsystemEdit(Subsystem subsystem, Model model) {
        Iterable<Projects> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "Subsystem/subsystem-edit";
    }

    @PostMapping("/subsystem/{id}/edit")
    public String subsystemPostEdit(@ModelAttribute("Subsystem") @Valid Subsystem subsystem, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Subsystem/subsystem-edit";
        }
        sybsystemRepository.save(subsystem);
        return "redirect:/project";
    }

    @PostMapping("/subsystem/{id}/remove")
    public String subsystemPostDelete(@PathVariable(value = "id") long id, Model model) {
        Subsystem subsystem = sybsystemRepository.findById(id).orElseThrow();
        sybsystemRepository.delete(subsystem);
        return "redirect:/project";
    }

}
