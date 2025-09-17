package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.ReviewHistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewLikeHistoryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewHistoryRepository reviewLikeHistoryRepository, ObjectMapper objectMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeHistoryRepository = reviewLikeHistoryRepository;
        this.objectMapper = objectMapper;
    }

    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public ResponseEntity<Review> addReview(Review newReview) {
        return ResponseEntity.ok(reviewRepository.save(newReview));
    }

    public ResponseEntity<ReviewLikeHistory> addReviewLikeHistory(Map<String, Object> requestBody) {
        ReviewLikeHistory newReviewLikeHistory = reviewLikeHistoryRepository.save(
                new ReviewLikeHistory(
                        String.valueOf(requestBody.get("likeType")),
                        objectMapper.convertValue(requestBody.get("review"), Review.class),
                        objectMapper.convertValue(requestBody.get("user"), User.class)
                )
        );

        return ResponseEntity.ok(newReviewLikeHistory);
    }

    public ResponseEntity<String> deleteReview(Long id){
        return null;
    }

    public ResponseEntity<Review> updateReview(Review updatedReview){
        return null;
    }

    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(Long id, Map<String, String> newLikeType){
        return null;
    }

    //----------------------------------------
    //Egyeb
    private Review setPatchedLikeDetails(Map<String, Integer> likeDetails, Review defaultReview) {
        ObjectNode baseReviewNode = objectMapper.convertValue(defaultReview, ObjectNode.class);
        ObjectNode likeDetailsNode = objectMapper.convertValue(likeDetails, ObjectNode.class);

        baseReviewNode.setAll(likeDetailsNode);

        return objectMapper.convertValue(baseReviewNode, Review.class);
    }

    /*
    * Validaciok:
    *           nem talalt user
    *           update --> nem talat review
    *           history --> nem talalt review & user
    * */
}
