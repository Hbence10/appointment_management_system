package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsRepository.findAll().stream().filter(news -> !news.getIsDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> addNewNews(News newNews) {
        if (newNews.getId() != null) {
            return ResponseEntity.status(422).build();
        } else {
            newNews.setTitle(newNews.getTitle().trim());
            return ResponseEntity.ok(newsRepository.save(newNews));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> addCoverImg(Long newsId, MultipartFile coverImg) {
        News searchedNews = newsRepository.findById(newsId).get();
        if (searchedNews.getId() == null || searchedNews.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\news" + File.separator + coverImg.getOriginalFilename();

            try {
                FileOutputStream fout = new FileOutputStream(filePath);
                fout.write(coverImg.getBytes());
                fout.close();

                searchedNews.setBannerImgPath("assets\\images\\news" + File.separator + coverImg.getOriginalFilename());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.ok().body(newsRepository.save(searchedNews));
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> updateNews(News updatedNews) {
        if (updatedNews.getId() == null || updatedNews.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            updatedNews.setTitle(updatedNews.getTitle().trim());
            return ResponseEntity.ok(newsRepository.save(updatedNews));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteNews(Long id) {
        News wantedNews = newsRepository.findById(id).get();

        if (wantedNews == null || wantedNews.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            wantedNews.setIsDeleted(true);
            wantedNews.setDeletedAt(new Date());
            newsRepository.save(wantedNews);

            return ResponseEntity.ok().build();
        }
    }
}
