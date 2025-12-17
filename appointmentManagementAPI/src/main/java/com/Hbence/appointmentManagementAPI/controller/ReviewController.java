package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //Review:
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Review>> getAllReview() {
        return reviewService.getAllReview();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PostMapping("/addReview")
    public ResponseEntity<Object> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        return reviewService.deleteReview(id);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return reviewService.updateReview(id, requestBody.get("text").asText());
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PostMapping("/addLike")
    public ResponseEntity<Object> addLike(@RequestBody ReviewHistoryWithReview reviewLike) {
        return reviewService.addLike(reviewLike);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @PutMapping("/changeLikeType/{id}")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(@PathVariable("id") Long id) {
        return reviewService.changeLikeTypeOfReview(id);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<Object> deleteLike(@PathVariable("id") Long id) {
        return reviewService.deleteReviewLike(id);
    }
}
