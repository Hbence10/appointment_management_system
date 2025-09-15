package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.other.Response;
import com.Hbence.appointmentManagementAPI.repository.ReviewHistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Response addReview(Review newReview) {
        reviewRepository.save(newReview);
//        return new Response(HttpStatus.OK.value(), "succes", LocalDateTime.now());
        return null;
    }

    public Review updateLikesOfReviews(Long id, Map<String, Integer> likeDetails) {
        Review defaultReview = reviewRepository.findById(id).get();
        Review patchedReview = setPatchedLikeDetails(likeDetails, defaultReview);

        return reviewRepository.save(patchedReview);
    }

    public void addReviewLikeHistory(Map<String, Object> requestBody) {
        reviewLikeHistoryRepository.save(
                new ReviewLikeHistory(
                        String.valueOf(requestBody.get("likeType")),
                        objectMapper.convertValue(requestBody.get("review"), Review.class),
                        objectMapper.convertValue(requestBody.get("user"), User.class)
                )
        );
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
