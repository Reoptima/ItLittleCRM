package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class reportController {

    @Autowired
    ProjectRepository projectRepository;
}
