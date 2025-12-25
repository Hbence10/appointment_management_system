package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
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
    public ResponseEntity<Object> addNewNews(News newNews) {
        try {
            if (newNews == null) {
                return ResponseEntity.status(422).build();
            }

            if (newNews.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else {
                newNews.setTitle(newNews.getTitle().trim());
                newNews.setText(newNews.getText().trim());
                return ResponseEntity.ok(newsRepository.save(newNews));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body("duplicateNewsTitle");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addCoverImg(Long newsId, MultipartFile coverImg) {
        try {
            if (newsId == null || coverImg == null) {
                return ResponseEntity.status(422).build();
            }

            News searchedNews = newsRepository.findById(newsId).orElse(null);
            if (searchedNews == null || searchedNews.getIsDeleted()) {
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
                    return ResponseEntity.internalServerError().body("errorWithFileUploading");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> updateNews(News updatedNews) {
        try {
            if (updatedNews == null) {
                return ResponseEntity.status(422).build();
            }

            News searchedNews = newsRepository.findById(updatedNews.getId()).orElse(null);
            if (searchedNews == null || searchedNews.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (updatedNews.getId() == null || updatedNews.getIsDeleted()) {
                return ResponseEntity.status(415).build();
            } else {
                updatedNews.setTitle(updatedNews.getTitle().trim());
                updatedNews.setText(updatedNews.getText().trim());
                return ResponseEntity.ok(newsRepository.save(updatedNews));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body("duplicateNewsTitle");
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

            News wantedNews = newsRepository.findById(id).orElse(null);

            if (wantedNews == null || wantedNews.getIsDeleted()) {
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

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */
