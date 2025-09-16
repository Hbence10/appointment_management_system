package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public List<Review> getAllReview() {
        return reviewService.getAllReview();
    }

    @PostMapping("")
    public ResponseEntity<Review> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @PatchMapping("/{id}")
    public Review updateLikesOfReviews(@PathVariable("id") Long id, @RequestBody Map<String, Integer> likeDetails) {
        return reviewService.updateLikesOfReviews(id, likeDetails);
    }

    @PostMapping("/likeHistory")
    public void addLikeHistory(@RequestBody Map<String, Object> requestBody) {
        reviewService.addReviewLikeHistory(requestBody);
    }


}
