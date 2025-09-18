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
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Gallery> updateGalleryImage(Gallery updatedGalleryImage) {
        return null;
    }

    //Szabalyzat:
    public ResponseEntity<Rules> getRule() {
        return ResponseEntity.ok(ruleRepository.findById(Long.valueOf(1)).get());
    }

    public ResponseEntity<Rules> updateRules(Rules updatedRules) {
        return null;
    }
}
