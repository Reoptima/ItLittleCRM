package com.example.itlittlecrm.controller;
import com.example.itlittlecrm.models.*;
import com.example.itlittlecrm.repo.NewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin
@RestController
public class LinkController {

    private final NewsRepository newsRepository; // Замените "YourRepository" на реальное имя вашего репозитория

    public LinkController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PostMapping("/saveData")
    public void saveData(@RequestBody String text) {
        News entity = new News();
        entity.setText(text);
        newsRepository.save(entity);
    }

    @GetMapping("/fetchUrl")
    public LinkResponse fetchUrl(@RequestParam("url") String url) {
        try {
            // Fetch the HTML content of the URL
            Document doc = Jsoup.connect(url).get();

            // Extract the necessary information from the HTML
            String title = doc.title();
            String description = doc.select("meta[name=description]").attr("content");
            String imageUrl = doc.select("meta[property=og:image]").attr("content");

            // Construct the LinkResponse object with the fetched data
            LinkResponse response = new LinkResponse();
            response.setSuccess(1);
            response.setLink(url);

            Meta meta = new Meta();
            meta.setTitle(title);
            meta.setDescription(description);

            Image image = new Image();
            image.setUrl(imageUrl);
            meta.setImage(image);

            response.setMeta(meta);

            // Return the LinkResponse object as JSON
            return response;
        } catch (IOException e) {
            // Handle any exceptions that occurred during the fetching process
            e.printStackTrace();

            // Construct an error response
            LinkResponse errorResponse = new LinkResponse();
            errorResponse.setSuccess(0);
            return errorResponse;
        }
    }
}
