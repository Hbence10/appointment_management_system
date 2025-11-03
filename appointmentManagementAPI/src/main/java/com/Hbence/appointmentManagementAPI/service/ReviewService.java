package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.repository.ReviewHistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewLikeHistoryRepository;
    private final UserRepository userRepository;

    //Review
    public ResponseEntity<List<Review>> getAllReview() {
        return ResponseEntity.ok().body(reviewRepository.findAll().stream().filter(review -> !review.getIsDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> addReview(Review newReview) {
        if (newReview.getId() != null) {
            return ResponseEntity.notFound().build();
        } else if (newReview.getRating() > 5 || newReview.getRating() < 0) {
            return ResponseEntity.status(409).body("");
        } else {
            newReview.setReviewText(newReview.getReviewText().trim());
            return ResponseEntity.ok(reviewRepository.save(newReview));
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<String> deleteReview(Long id) {
        Review searchedReview = reviewRepository.findById(id).get();

        if (searchedReview == null || searchedReview.getAuthor().getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedReview.setIsDeleted(true);
            searchedReview.setDeletedAt(LocalDateTime.now());
            reviewRepository.save(searchedReview);
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Review> updateReview(Long id, String updatedReviewText) {
        Review searchedReview = reviewRepository.findById(id).get();

        if (searchedReview == null || searchedReview.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedReview.setReviewText(updatedReviewText.trim());
            return ResponseEntity.ok().body(reviewRepository.save(searchedReview));
        }
    }

    //--------
    //ReviewLike
    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<ReviewLikeHistory> addLike(ReviewHistoryWithReview reviewLike) {
        if (reviewLike.getId() != null) {
            return ResponseEntity.notFound().build();
        } else {
            ReviewLikeHistory reviewLikeHistory = new ReviewLikeHistory(reviewLike.getLikeType(), reviewLike.getLikedReview(), reviewLike.getLikerUser());
            System.out.println(reviewLikeHistory);
            return ResponseEntity.ok(reviewLikeHistoryRepository.save(reviewLikeHistory));
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(Long id) {
        ReviewLikeHistory searchedReviewLike = reviewLikeHistoryRepository.findById(id).get();
        System.out.println(searchedReviewLike);
        if (searchedReviewLike.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            String originalLikeType = searchedReviewLike.getLikeType();
            if (originalLikeType.equals("like")) {
                searchedReviewLike.setLikeType("dislike");
            } else {
                searchedReviewLike.setLikeType("like");
            }
            return ResponseEntity.ok(searchedReviewLike);
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> deleteReviewLike(Long id) {
        ReviewLikeHistory searchedReviewLike = reviewLikeHistoryRepository.findById(id).get();
        if (searchedReviewLike.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            reviewLikeHistoryRepository.delete(searchedReviewLike);
            return ResponseEntity.ok().build();
        }
    }
}
