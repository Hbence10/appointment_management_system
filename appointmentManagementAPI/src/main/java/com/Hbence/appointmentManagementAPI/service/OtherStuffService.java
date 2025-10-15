package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.GalleryRepository;
import com.Hbence.appointmentManagementAPI.repository.HistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.RuleRepository;
import com.Hbence.appointmentManagementAPI.repository.SpecialOfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final RuleRepository ruleRepository;
    private final GalleryRepository galleryRepository;
    private final HistoryRepository historyRepository;
    private final SpecialOfferRepository specialOfferRepository;

    //Galleria:
    public ResponseEntity<List<Gallery>> getGalleryImages() {
        return ResponseEntity.ok(galleryRepository.findAll());
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
        updatedRules.setLastEditAt(LocalDateTime.now());
        return ResponseEntity.ok(ruleRepository.save(updatedRules));
    }

    //History
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> getHistory(){
        return null;
    }
}
