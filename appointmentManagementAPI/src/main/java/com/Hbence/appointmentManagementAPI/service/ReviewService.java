package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.ReviewHistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewLikeHistoryRepository;
    private final ObjectMapper objectMapper;

    //Review
    public ResponseEntity<List<Review>> getAllReview() {
        return ResponseEntity.ok().body(reviewRepository.findAll().stream().filter(review -> !review.isDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Review> addReview(Review newReview) {
        if (newReview.getId() != null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reviewRepository.save(newReview));
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<String> deleteReview(Long id) {
        return null;
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Review> updateReview(Review updatedReview) {
        return null;
    }

    //ReviewLike
    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<ReviewLikeHistory> addLike(ReviewLikeHistory reviewLike) {

        return null;
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(Long id, Map<String, String> newLikeType) {
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
