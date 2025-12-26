package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class GalleryService {
    private final GalleryRepository galleryRepository;

    public ResponseEntity<List<Gallery>> getGalleryImages() {
        try {
            return ResponseEntity.ok(galleryRepository.getGalleryImages());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> updateGalleryImage(MultipartFile galleryImg, Long id) {
        try {
            if (galleryImg == null || id == null) {
                return ResponseEntity.status(422).build();
            }

            Gallery searchedImg = galleryRepository.findById(id).orElse(null);
            if (searchedImg == null) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\gallery" + File.separator + galleryImg.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(galleryImg.getBytes());
                    fout.close();
                    searchedImg.setPhotoPath("\"assets\\\\images\\\\gallery\" + File.separator + galleryImg.getOriginalFilename()");
                    searchedImg.setPhotoName(galleryImg.getOriginalFilename());
                    return ResponseEntity.ok().body(galleryRepository.save(searchedImg));
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("fileUploadError");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addGalleryImage(MultipartFile galleryImg, Integer placement) {
        try {
            if (galleryImg == null || placement == null) {
                return ResponseEntity.status(422).build();
            }

            String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\gallery" + File.separator + galleryImg.getOriginalFilename();

            try {
                FileOutputStream fout = new FileOutputStream(filePath);
                fout.write(galleryImg.getBytes());
                fout.close();
                Gallery newImg = new Gallery(galleryImg.getOriginalFilename(), "assets\\images\\gallery" + File.separator + galleryImg.getOriginalFilename(), placement);
                return ResponseEntity.ok().body(galleryRepository.save(newImg));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("fileUploadError");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("serverError");
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> deleteGalleryImage(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Gallery searchedImage = galleryRepository.findById(id).orElse(null);
            if (searchedImage == null || searchedImage.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedImage.setIsDeleted(true);
                searchedImage.setDeletedAt(new Date());
                galleryRepository.save(searchedImage);
                return ResponseEntity.ok().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updateOrder(List<Gallery> updatedOrderList) {
        try {
            if (updatedOrderList == null || updatedOrderList.isEmpty()) {
                return ResponseEntity.status(422).build();
            }

            return ResponseEntity.ok(galleryRepository.saveAll(updatedOrderList));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
