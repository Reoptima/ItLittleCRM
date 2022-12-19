package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.models.Task;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class TaskController {
    @Autowired
    SubsystemController subsystemController;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/subsystem/{subsystem}/tasks/add")
    public String taskAdd(Subsystem subsystem, Projects projects, Task task, @RequestParam("redirect") String redirect, Model model, BindingResult bindingResult) {
        return getString(model, task, projects, subsystem, redirect);
    }

    private String getString(Model model, Task task, Projects projects, Subsystem subsystem, String redirect) {
        task.setSubsystem(subsystem);
        model.addAttribute("redirect", redirect);
        return "Task/task-add";
    }

    @PostMapping("/subsystem/{subsystem}/tasks/add")
    public String taskPostAdd(@ModelAttribute("Task") Task task, Projects projects, Subsystem subsystem, Model model, String redirect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, task, projects, projects.getSubsystems().get(0), redirect);
        }
        taskRepository.save(task);
        return "redirect:" + redirect;
    }

    @GetMapping("/tasks/{task}")
    public String taskDetail(Task task) {
        return "Task/task-details";
    }

    @GetMapping("/tasks/{task}/edit")
    private String taskEdit(Task task, Model model) {
        return "Task/task-edit";
    }

    @PostMapping("/tasks/{id}/edit")
    public String taskPostEdit(@ModelAttribute("Task") @Valid Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Task/task-edit";
        }
        taskRepository.save(task);
        return "redirect:/project/";
    }

    @PostMapping("/tasks/{id}/delete")
    public String taskDelete(Task task) {
        taskRepository.delete(task);
        return "redirect:/project/";
    }
}