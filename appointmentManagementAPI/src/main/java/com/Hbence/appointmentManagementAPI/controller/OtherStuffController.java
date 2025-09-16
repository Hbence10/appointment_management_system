package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    @Autowired
    public OtherStuffController(OtherStuffService otherStuffService) {
        this.otherStuffService = otherStuffService;
    }

    //Galleria:
    @GetMapping("/gallery")
    public List<Gallery> getAllGalleryImages() {
        return otherStuffService.getGalleryImages();
    }

    @PutMapping("/gallery")
    public ResponseEntity<Gallery> updateGalleryImage(@RequestBody Gallery updatedGallery){
        return otherStuffService.updateGalleryImage(updatedGallery);
    }

    //Szabalyzat:
    @GetMapping("/rule")
    public Rules getRule() {
        return otherStuffService.getRule();
    }

    @PutMapping("/rule")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule){
        return otherStuffService.updateRules(updatedRule);
    }
}
