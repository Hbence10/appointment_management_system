package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/news")
public class NewsController {

    public final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @GetMapping("")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping("")
    public ResponseEntity<News> addNewNews(@RequestBody News newNews){
        return newsService.addNewNews(newNews);
    }

    @PutMapping("")
    public ResponseEntity<News> updateNews(@RequestBody News updatedNews){
        return newsService.updateNews(updatedNews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id){
        return newsService.deleteNews(id);
    }
}
