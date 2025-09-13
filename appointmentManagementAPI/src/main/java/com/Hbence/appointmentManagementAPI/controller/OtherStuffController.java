package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import com.Hbence.appointmentManagementAPI.service.Response;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    @Autowired
    public OtherStuffController(OtherStuffService otherStuffService) {
        this.otherStuffService = otherStuffService;
    }


    //Velemenyek
    @GetMapping("/reviews")
    public List<Review> getAllReview(){
        return otherStuffService.getAllReview();
    }

    @PostMapping("/reviews")
    public Response addReview(@RequestBody Review newReview){
        return otherStuffService.addReview(newReview);
    }

    //Galleria:
    @GetMapping("/gallery")
    public List<Gallery> getAllGalleryImages(){
        return otherStuffService.getGalleryImages();
    }

    //Szabalyzat:
    @GetMapping("/rule")
    public Rules getRule(){
        return otherStuffService.getRule();
    }
}
