package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.Comments;
import com.example.itlittlecrm.models.Task;
import com.example.itlittlecrm.repo.CommentsRepository;
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
public class CommentsController {

    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks/{task}/comments/add")
    public String commentsAdd(Comments comments, Task task, @RequestParam("redirect") String redirect, Model model) {
        return getString(model, comments, task, redirect);
    }

    private String getString(Model model, Comments comments, Task task, String redirect) {
        comments.setTask(task);
        model.addAttribute("redirect", redirect);
        return "Comments/comments-add";
    }

    @PostMapping("/tasks/{task}/comments/add")
    public String commentsPostAdd(@ModelAttribute("Comments") Comments comments, Task task, Model model, String redirect, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model, comments, task, redirect);
        }
        commentsRepository.save(comments);
        return "redirect:" + redirect;
    }

    @GetMapping("/comments/{comments}")
    public String commentsDetail(Comments comments) {
        return "Comments/comments-details";
    }

    @GetMapping("/comments/{comments}/edit")
    private String commentsEdit(Comments comments, Model model) {
        return "Comments/comments-edit";
    }

    @PostMapping("/comments/{id}/edit")
    public String commentsPostEdit(@ModelAttribute("Comments") @Valid Comments comments, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Comments/comments-edit";
        }
        commentsRepository.save(comments);
        return "redirect:/project/";
    }

    @PostMapping("/comments/{id}/delete")
    public String commentsPostDelete(@ModelAttribute("Comments") Comments comments) {
        commentsRepository.delete(comments);
        return "redirect:/project/";
    }
}