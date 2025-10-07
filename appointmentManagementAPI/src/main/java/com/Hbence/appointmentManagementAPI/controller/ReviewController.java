package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
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
    @GetMapping("/getAll")
    public ResponseEntity<List<Review>> getAllReview() {
        return reviewService.getAllReview();
    }

    @PostMapping("/addReview")
    public ResponseEntity<Object> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        return reviewService.deleteReview(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody Map<String, String> requestBody) {
        return reviewService.updateReview(id, requestBody.get("text"));
    }

    //LikeHistory
    @PostMapping("/addLike")
    public ResponseEntity<ReviewLikeHistory> addLike(@RequestBody ReviewHistoryWithReview reviewLike) {
        return reviewService.addLike(reviewLike);
    }

    @PutMapping("/changeLikeType/{id}")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(@PathVariable("id") Long id) {
        return reviewService.changeLikeTypeOfReview(id);
    }

    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<Object> deleteLike(@PathVariable("id") Long id){
        return reviewService.deleteReviewLike(id);
    }
}
