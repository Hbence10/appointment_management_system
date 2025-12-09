package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
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
    private final DetailsRepository detailsRepository;
    private final OpeningDetailsRepository openingDetailsRepository;

    //Galleria:
    public ResponseEntity<List<Gallery>> getGalleryImages() {
        return ResponseEntity.ok(galleryRepository.findAll().stream().filter(image -> !image.getIsDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Gallery> updateGalleryImage(Gallery updatedGalleryImage) {
        return ResponseEntity.ok(galleryRepository.save(updatedGalleryImage));
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

    //Adatok
    public ResponseEntity<Details> getDetails(){
        return ResponseEntity.ok().body(detailsRepository.findById(1).orElse(null));
    }

    public ResponseEntity<Details> updateDetails(Details updatedDetails){
        return null;
    }

    public ResponseEntity<List<OpeningDetails>> getOpeningDetails(){
        return ResponseEntity.ok().body(openingDetailsRepository.findAll());
    }

    public ResponseEntity<OpeningDetails> updateOpeningDetails(){
        return null;
    }

    public ResponseEntity<OpeningDetails> addOpeningDetails(){
        return null;
    }


}
