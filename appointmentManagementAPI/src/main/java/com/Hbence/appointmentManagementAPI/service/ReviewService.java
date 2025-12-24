package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.ReviewHistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.ReviewRepository;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewLikeHistoryRepository;
    private final UserRepository userRepository;

    //Review
    public ResponseEntity<List<Review>> getAllReview() {
        try {
            return ResponseEntity.ok().body(reviewRepository.findAll().stream().filter(review -> !review.getIsDeleted()).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> addReview(Review newReview) {
        try {
            if (newReview == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(newReview.getAuthor().getId()).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            if (newReview.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (newReview.getRating() > 5 || newReview.getRating() < 0) {
                return ResponseEntity.status(415).body("invalidRating");
            } else {
                newReview.setReviewText(newReview.getReviewText().trim());
                return ResponseEntity.ok(reviewRepository.save(newReview));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<String> deleteReview(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Review searchedReview = reviewRepository.findById(id).orElse(null);

            if (searchedReview == null || searchedReview.getAuthor().getIsDeleted() || searchedReview.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedReview.setIsDeleted(true);
                searchedReview.setDeletedAt(new Date());
                reviewRepository.save(searchedReview);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Review> updateReview(Long id, String updatedReviewText) {
        try {
            if (id == null || updatedReviewText == null) {
                return ResponseEntity.status(422).build();
            }

            Review searchedReview = reviewRepository.findById(id).orElse(null);

            if (searchedReview == null || searchedReview.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedReview.setReviewText(updatedReviewText.trim());
                return ResponseEntity.ok().body(reviewRepository.save(searchedReview));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //ReviewLike
    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> addLike(ReviewHistoryWithReview reviewLike) {
        try {
            if (reviewLike == null) {
                return ResponseEntity.status(422).build();
            }

            if (reviewLike.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (!reviewLike.getLikeType().equals("like") && !reviewLike.getLikeType().equals("dislike")) {
                return ResponseEntity.status(415).body("invalidLikeType");
            } else {
                ReviewLikeHistory reviewLikeHistory = new ReviewLikeHistory(reviewLike.getLikeType(), reviewLike.getLikedReview(), reviewLike.getLikerUser());
                return ResponseEntity.ok(reviewLikeHistoryRepository.save(reviewLikeHistory));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            ReviewLikeHistory searchedReviewLike = reviewLikeHistoryRepository.findById(id).orElse(null);

            if (searchedReviewLike == null) {
                return ResponseEntity.notFound().build();
            } else {
                String originalLikeType = searchedReviewLike.getLikeType();
                if (originalLikeType.equals("like")) {
                    searchedReviewLike.setLikeType("dislike");
                } else {
                    searchedReviewLike.setLikeType("like");
                }
                return ResponseEntity.ok(reviewLikeHistoryRepository.save(searchedReviewLike));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> deleteReviewLike(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            ReviewLikeHistory searchedReviewLike = reviewLikeHistoryRepository.findById(id).orElse(null);
            if (searchedReviewLike == null) {
                return ResponseEntity.notFound().build();
            } else {
                reviewLikeHistoryRepository.delete(searchedReviewLike);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */