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
        try {
            return ResponseEntity.ok(newsRepository.findAll().stream().filter(news -> !news.getIsDeleted()).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> addNewNews(News newNews) {
        try {
            if (newNews == null) {
                return ResponseEntity.status(422).build();
            }

            if (newNews.getId() != null) {
                return ResponseEntity.status(422).build();
            } else {
                newNews.setTitle(newNews.getTitle().trim());
                return ResponseEntity.ok(newsRepository.save(newNews));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> addCoverImg(Long newsId, MultipartFile coverImg) {
        try {
            if (newsId == null || coverImg == null) {
                return ResponseEntity.status(422).build();
            }

            News searchedNews = newsRepository.findById(newsId).get();
            if (searchedNews == null || searchedNews.getId() == null || searchedNews.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\news" + File.separator + coverImg.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(coverImg.getBytes());
                    fout.close();

                    searchedNews.setBannerImgPath("assets\\images\\news" + File.separator + coverImg.getOriginalFilename());
                    return ResponseEntity.ok().body(newsRepository.save(searchedNews));
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<News> updateNews(News updatedNews) {
        try {
            if (updatedNews == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedNews.getId() == null || updatedNews.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                updatedNews.setTitle(updatedNews.getTitle().trim());
                return ResponseEntity.ok(newsRepository.save(updatedNews));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteNews(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            News wantedNews = newsRepository.findById(id).get();

            if (wantedNews == null || wantedNews.getId() == null || wantedNews.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                wantedNews.setIsDeleted(true);
                wantedNews.setDeletedAt(new Date());
                newsRepository.save(wantedNews);

                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
