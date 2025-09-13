package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OtherStuffService {

    private final ReviewRepository reviewRepository;
    private final RuleRepository ruleRepository;
    private final GalleryRepository galleryRepository;
    private final HistoryRepository historyRepository;
    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public OtherStuffService(ReviewRepository reviewRepository, RuleRepository ruleRepository, GalleryRepository galleryRepository, HistoryRepository historyRepository, SpecialOfferRepository specialOfferRepository) {
        this.reviewRepository = reviewRepository;
        this.ruleRepository = ruleRepository;
        this.galleryRepository = galleryRepository;
        this.historyRepository = historyRepository;
        this.specialOfferRepository = specialOfferRepository;
    }

    //Velemenyek:
    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public Response addReview(Review newReview) {
        reviewRepository.save(newReview);
        return new Response(HttpStatus.OK.value(), "succes", LocalDateTime.now());
    }

    public String updateLikesOfReviews(Long id, String addedLikeType) {
        Review searchedReview = reviewRepository.findById(id).get();

        if (addedLikeType.equals("dislike")) {
            searchedReview.setDislikeCount(searchedReview.getDislikeCount() + 1);
        } else if (addedLikeType.equals("like")){
            searchedReview.setLikeCount(searchedReview.getLikeCount() + 1);
        } else {
            //exception-t kell majd dobnia
        }

        reviewRepository.save(searchedReview);

        return "";
    }

    //Galleria:
    public List<Gallery> getGalleryImages() {
        return galleryRepository.findAll();
    }

    //Szabalyzat:
    public Rules getRule() {
        return ruleRepository.findById(1).get();
    }
}
