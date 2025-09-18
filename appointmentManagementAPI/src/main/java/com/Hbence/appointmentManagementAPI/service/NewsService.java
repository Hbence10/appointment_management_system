package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

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
