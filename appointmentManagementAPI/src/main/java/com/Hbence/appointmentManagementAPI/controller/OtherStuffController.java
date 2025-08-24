package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class OtherStuffController {

    private OtherStuffService otherStuffService;

    @Autowired
    public OtherStuffController(OtherStuffService otherStuffService) {
        this.otherStuffService = otherStuffService;
    }

    @GetMapping("/rule")
    public List<Gallery> getGallery(){
        return otherStuffService.getAllGalleryPhoto();
    }

    @GetMapping("/reviews")
    public List<Review> getAllReview(){
        return otherStuffService.getAllReview();
    }

//    @GetMapping("/")
}

/*
 * Endpointok:
 *       - galleria lekerdezese
 *       - galleria szerkesztese
 *       - szabalyzat lekerdezese
 *       - szabalyzat szerkesztese
 *       - akcio lekerdezese
 *       - akcio letrehozasa
 *       - velemenyek lekerdezese
 *       - velemeny like/dislike
 * */
