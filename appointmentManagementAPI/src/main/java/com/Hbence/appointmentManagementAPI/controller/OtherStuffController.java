package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //Szabalyzat:
    @GetMapping("/rule")
    public Rules getRule() {
        return otherStuffService.getRule();
    }
}
