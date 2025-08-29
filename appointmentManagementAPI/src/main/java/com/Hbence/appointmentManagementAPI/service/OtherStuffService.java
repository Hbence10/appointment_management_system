package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.History;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OtherStuffService {

    private ReviewRepository reviewRepository;
    private RuleRepository ruleRepository;
    private GalleryRepository galleryRepository;
    private HistoryRepository historyRepository;
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    public OtherStuffService(ReviewRepository reviewRepository, RuleRepository ruleRepository, GalleryRepository galleryRepository, HistoryRepository historyRepository, SpecialOfferRepository specialOfferRepository) {
        this.reviewRepository = reviewRepository;
        this.ruleRepository = ruleRepository;
        this.galleryRepository = galleryRepository;
        this.historyRepository = historyRepository;
        this.specialOfferRepository = specialOfferRepository;
    }

    public Rules getRules(){
        return ruleRepository.findById(1).get();
    }

    public List<Gallery> getAllGalleryPhoto(){
        return galleryRepository.findAll();
    }

    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }

    //Velemenyek:
    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    public Response addReview(Map<String, Object> newReview){
        reviewRepository.addedReview(
                newReview.get("userId"),
                newReview.get("reviewText"),
                newReview.get("rating")
        );

        return new Response(HttpStatus.OK.value(), "succes", LocalDateTime.now());
    }

    public List<Gallery> getGalleryImages(){
        return galleryRepository.findAll();
    }
}
