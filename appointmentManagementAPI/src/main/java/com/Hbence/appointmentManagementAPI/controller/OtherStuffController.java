package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import com.Hbence.appointmentManagementAPI.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class OtherStuffController {

    private OtherStuffService otherStuffService;

    @Autowired
    public OtherStuffController(OtherStuffService otherStuffService) {
        this.otherStuffService = otherStuffService;
    }

    @GetMapping("/gallery")
    public List<Gallery> getGallery(){
        return otherStuffService.getAllGalleryPhoto();
    }


    //Velemenyek
    @GetMapping("/reviews")
    public List<Review> getAllReview(){
        return otherStuffService.getAllReview();
    }

    @PostMapping("/reviews")
    public Response addReview(@RequestBody Map<String, Object> newReview){
        return otherStuffService.addReview(newReview);
    }
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
