package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private final ObjectMapper objectMapper;

    @Autowired
    public OtherStuffService(ReviewRepository reviewRepository, RuleRepository ruleRepository, GalleryRepository galleryRepository, HistoryRepository historyRepository, SpecialOfferRepository specialOfferRepository, ObjectMapper objectMapper) {
        this.reviewRepository = reviewRepository;
        this.ruleRepository = ruleRepository;
        this.galleryRepository = galleryRepository;
        this.historyRepository = historyRepository;
        this.specialOfferRepository = specialOfferRepository;
        this.objectMapper = objectMapper;
    }

    //Velemenyek:
    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public Response addReview(Review newReview) {
        reviewRepository.save(newReview);
        return new Response(HttpStatus.OK.value(), "succes", LocalDateTime.now());
    }

    public Review updateLikesOfReviews(Long id, Map<String, Integer> likeDetails) {
        Review defaultReview = reviewRepository.findById(id).get();
        Review patchedReview = setPatchedLikeDetails(likeDetails, defaultReview);

        return reviewRepository.save(patchedReview);
    }

    //Galleria:
    public List<Gallery> getGalleryImages() {
        return galleryRepository.findAll();
    }

    //Szabalyzat:
    public Rules getRule() {
        return ruleRepository.findById(Long.valueOf(1)).get();
    }

    //----------------------------------------
    //Egyeb:
    private Review setPatchedLikeDetails(Map<String, Integer> likeDetails, Review defaultReview){
        ObjectNode baseReviewNode = objectMapper.convertValue(defaultReview, ObjectNode.class);
        ObjectNode likeDetailsNode = objectMapper.convertValue(likeDetails, ObjectNode.class);

        baseReviewNode.setAll(likeDetailsNode);

        return objectMapper.convertValue(baseReviewNode, Review.class)  ;
    }

    /*
    * ObjectMapper:
    *
    *
    * */
}
