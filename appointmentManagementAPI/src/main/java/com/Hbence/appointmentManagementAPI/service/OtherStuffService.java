package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.History;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.GalleryRepository;
import com.Hbence.appointmentManagementAPI.repository.HistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.RuleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final RuleRepository ruleRepository;
    private final GalleryRepository galleryRepository;
    private final HistoryRepository historyRepository;

    //Galleria:
    public ResponseEntity<List<Gallery>> getGalleryImages() {
        return ResponseEntity.ok(galleryRepository.findAll().stream().filter(image -> !image.getIsDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Gallery> updateGalleryImage(Gallery updatedGalleryImage) {
        return ResponseEntity.ok(galleryRepository.save(updatedGalleryImage));
    }

    public ResponseEntity<Gallery> addGalleryImage(Gallery newImage){
        return null;
    }

    public ResponseEntity<Object> deleteGalleryImage(Long id) {
        Gallery searchedImage = galleryRepository.findById(id).get();
        if(searchedImage == null || searchedImage.getId() == null || searchedImage.getIsDeleted()){
            return ResponseEntity.notFound().build();
        } else {
            searchedImage.setIsDeleted(true);
            searchedImage.setDeletedAt(new Date());
            galleryRepository.save(searchedImage);

            return ResponseEntity.ok().build();
        }
    }

    //Szabalyzat:
    public ResponseEntity<Rules> getRule() {
        return ResponseEntity.ok(ruleRepository.findById(Long.valueOf(1)).get());
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Rules> updateRules(Rules updatedRules) {
        if (updatedRules.getId() > 1) {
            return ResponseEntity.notFound().build();
        } else {
            updatedRules.setLastEditAt(LocalDateTime.now());
            return ResponseEntity.ok(ruleRepository.save(updatedRules));
        }
    }

    //History
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<History>> getHistory(){
        return ResponseEntity.ok().body(historyRepository.findAll());
    }
}
