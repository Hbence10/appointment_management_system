package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //Review:
    @GetMapping("")
    public List<Review> getAllReview() {
        return reviewService.getAllReview();
    }

    @PostMapping("")
    public ResponseEntity<Review> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        return reviewService.deleteReview(id);
    }

    @PutMapping("")
    public ResponseEntity<Review> updateReview(@RequestBody Review updatedReview) {
        return reviewService.updateReview(updatedReview);
    }

    //LikeHistory
    @PostMapping("/likeHistory")
    public ResponseEntity<ReviewLikeHistory> addLikeHistory(@RequestBody Map<String, Object> requestBody) {
        return reviewService.addReviewLikeHistory(requestBody);
    }

    @PatchMapping("/review/{id}")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(@PathVariable("id") Long id, Map<String, String> newLikeType) {
        return reviewService.changeLikeTypeOfReview(id, newLikeType);
    }
}
