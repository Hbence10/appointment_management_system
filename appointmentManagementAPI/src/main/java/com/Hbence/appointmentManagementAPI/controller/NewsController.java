package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    public NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /*
    *   A kovetkezo endpointok fognak kelleni:
    *           - Az osszes news kilistazasa (GET)
    *           - Egy db news megszerzese id alapjan (GET)
    *           - News letrehozasa (POST)
    *           - News szerkesztese (PUT/PATCH)
    *           - News torlese (PATCH --> nem veglegesen toroljuk az adatbazisbol)
    * */

    @GetMapping("/")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }
}
