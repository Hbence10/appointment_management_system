package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Rules;
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

    @PutMapping("/gallery")
    public ResponseEntity<Gallery> updateGalleryImage(@RequestBody Gallery updatedGallery) {
        return otherStuffService.updateGalleryImage(updatedGallery);
    }

    //Szabalyzat:
    @GetMapping("/rule")
    public ResponseEntity<Rules> getRule() {
        return otherStuffService.getRule();
    }

    @PutMapping("/rule")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule) {
        return otherStuffService.updateRules(updatedRule);
    }
}
