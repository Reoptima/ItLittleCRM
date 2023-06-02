package com.example.itlittlecrm.controller;

import com.example.itlittlecrm.models.News;
import com.example.itlittlecrm.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NewsController {
    @GetMapping("/news")
    public String News() {
        return "News/news-main";
    }
    @GetMapping("/news-list")
    public String NewsList() {
        return "News/news-list";
    }
}
