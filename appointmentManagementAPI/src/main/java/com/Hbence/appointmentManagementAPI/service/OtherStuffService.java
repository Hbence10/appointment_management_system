package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.History;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.*;
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

    //Velemenyek megszerzese
    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    //Szabalyzat megszerzese
    public Rules getRules(){
        return ruleRepository.findById(1).get();
    }

    //Galleria megszerzese
    public List<Gallery> getAllGalleryPhoto(){
        return galleryRepository.findAll();
    }

    //History megszerzese:
    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }
}
