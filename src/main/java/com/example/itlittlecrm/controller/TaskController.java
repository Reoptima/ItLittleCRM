package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.models.Task;
import com.example.itlittlecrm.repo.ProjectRepository;
import com.example.itlittlecrm.repo.TaskRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        @GetMapping("/task/export")
    public void mainExelExport(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=AllHrOrCommissionExport_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);


        Task[] tasks = Iterables.toArray(taskRepository.findAll(), Task.class);


        ExelExport exelExport = new ExelExport(tasks);
        exelExport.generateExcelFile(response);
    }
}