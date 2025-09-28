package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    @GetMapping("/getAll")
    public ResponseEntity<List<News>> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping("/addNews")
    public ResponseEntity<Object> addNewNews(@RequestBody News newNews){
        return newsService.addNewNews(newNews);
    }

    @PutMapping("/update")
    public ResponseEntity<News> updateNews(@RequestBody News updatedNews){
        return newsService.updateNews(updatedNews);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id){
        return newsService.deleteNews(id);
    }
}
