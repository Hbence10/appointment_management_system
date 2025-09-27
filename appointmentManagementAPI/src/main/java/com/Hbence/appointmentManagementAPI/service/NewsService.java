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

    public ResponseEntity<Object> addNewNews(News newNews){
        if(newNews.getId() != null){
            return ResponseEntity.status(422).body("invalidInput");
        } else {
            newNews.setTitle(newNews.getTitle().trim());
            return ResponseEntity.ok(newsRepository.save(newNews));
        }
    }

    public ResponseEntity<News> updateNews(News updatedNews){
        if(updatedNews.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            updatedNews.setTitle(updatedNews.getTitle().trim());
            return ResponseEntity.ok(newsRepository.save(updatedNews));
        }
    }

    public ResponseEntity<String> deleteNews(Long id){
        return null;
    }
}
