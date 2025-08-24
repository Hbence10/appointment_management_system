package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.GalleryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.Hbence.appointmentManagementAPI.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@Service
public class OtherStuffService {

    private ReviewRepository reviewRepository;
    private RuleRepository ruleRepository;
    private GalleryRepository galleryRepository;

    @Autowired
    public OtherStuffService(ReviewRepository reviewRepository, RuleRepository ruleRepository, GalleryRepository galleryRepository) {
        this.reviewRepository = reviewRepository;
        this.ruleRepository = ruleRepository;
        this.galleryRepository = galleryRepository;
    }

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    public Rules getRules(){
        return ruleRepository.findById(1).get();
    }

    public List<Gallery> getAllGalleryPhoto(){
        return galleryRepository.findAll();
    }
}
