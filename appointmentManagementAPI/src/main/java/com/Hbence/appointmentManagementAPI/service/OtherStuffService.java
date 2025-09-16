package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OtherStuffService {

    private final RuleRepository ruleRepository;
    private final GalleryRepository galleryRepository;
    private final HistoryRepository historyRepository;
    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public OtherStuffService(RuleRepository ruleRepository, GalleryRepository galleryRepository, HistoryRepository historyRepository, SpecialOfferRepository specialOfferRepository) {
        this.ruleRepository = ruleRepository;
        this.galleryRepository = galleryRepository;
        this.historyRepository = historyRepository;
        this.specialOfferRepository = specialOfferRepository;
    }

    //Galleria:
    public List<Gallery> getGalleryImages() {
        return galleryRepository.findAll();
    }

    public ResponseEntity<Gallery> updateGalleryImage(Gallery updatedGalleryImage){
        return null;
    }

    //Szabalyzat:
    public Rules getRule() {
        return ruleRepository.findById(Long.valueOf(1)).get();
    }

    public ResponseEntity<Rules> updateRules(Rules updatedRules){
        return null;
    }
}
