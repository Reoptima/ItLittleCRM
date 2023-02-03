package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Reports;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class reportController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ReportsRepository reportsRepository;

    @GetMapping("/project/{projects}/report/add")
    @PreAuthorize("hasAnyAuthority('DEVLEAD','ADMIN')")
    public String reportAdd(Reports reports, Projects projects, @RequestParam("redirect") String redirect, Model model) {
        return getString(model, reports, projects, redirect);
    }

    private String getString(Model model, Reports reports, Projects projects, String redirect) {
        reports.setProjects(projects);
        model.addAttribute("redirect", redirect);
        return "Report/report-add";
    }

    @PostMapping("/project/{projects}/report/add")
    public String reportPostAdd(@ModelAttribute("Reports") Reports reports, Projects projects, Model model, String redirect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, reports, projects, redirect);
        }
        reportsRepository.save(reports);
        return "redirect:" + redirect;
    }

    @GetMapping("/reports/{reports}")
    public String reportDetail(Reports reports) {
        return "Report/report-details";
    }

    @GetMapping("/reports/{reports}/edit")
    private String reportEdit(Reports reports, Model model) {
        return "Report/report-edit";
    }

    @PostMapping("/reports/{id}/edit")
    public String reportPostEdit(@ModelAttribute("Reports") @Valid Reports reports, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Report/report-edit";
        }
        reportsRepository.save(reports);
        return "redirect:/project";
    }

    @PostMapping("/reports/{id}/remove")
    public String reportPostDelete(@PathVariable("id") long id, Model model) {
        Reports reports = reportsRepository.findById(id).orElseThrow();
        reportsRepository.delete(reports);
        return "redirect:/project";
    }
}
