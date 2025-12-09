package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.CloseReasonRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedDateRepository;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    //Galleria:
    @GetMapping("/gallery")
    public ResponseEntity<List<Gallery>> getAllGalleryImages() {
        return otherStuffService.getGalleryImages();
    }

    @PutMapping("/gallery/update")
    public ResponseEntity<Gallery> updateGalleryImage(@RequestBody Gallery updatedGallery) {
        return otherStuffService.updateGalleryImage(updatedGallery);
    }

    //Szabalyzat:
    @GetMapping("/rule")
    public ResponseEntity<Rules> getRule() {
        return otherStuffService.getRule();
    }

    @PutMapping("/rule/update")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule) {
        return otherStuffService.updateRules(updatedRule);
    }

    //History
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistory(){
        return otherStuffService.getHistory();
    }

    //Adatok
    @GetMapping("/details")
    public ResponseEntity<Details> getDetails(){
        return otherStuffService.getDetails();
    }

    @PutMapping("/details/update")
    public ResponseEntity<Details> updateDetails(@RequestBody Details updatedDetails){
        return null;
    }

    @GetMapping("/openingDetails")
    public ResponseEntity<List<OpeningDetails>> getOpeningDetails(){
        return otherStuffService.getOpeningDetails();
    }

    @PutMapping("/openingDetails/update")
    public ResponseEntity<OpeningDetails> updateOpeningDetails(@RequestBody OpeningDetails updatedOpeningDetails){
        return null;
    }

    @PostMapping("/openingDetails/add")
    public ResponseEntity<OpeningDetails> addOpeningDetails(@RequestBody OpeningDetails addedOpeningDetails){
        return null;
    }
}
