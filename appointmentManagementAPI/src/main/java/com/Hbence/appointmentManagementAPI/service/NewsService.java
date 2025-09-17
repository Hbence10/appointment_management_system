package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ResponseEntity<List<News>> getAllNews(){
        return ResponseEntity.ok(newsRepository.findAll());
    }

    public ResponseEntity<News> addNewNews(News newNews){
        return null;
    }

    public ResponseEntity<News> updateNews(News updatedNews){
        return null;
    }

    public ResponseEntity<String> deleteNews(Long id){
        return null;
    }
}
