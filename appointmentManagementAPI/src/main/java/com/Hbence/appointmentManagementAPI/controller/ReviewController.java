package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Review-k visszaszerzése", description = "Az összes review megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Review>> getAllReview() {
        return reviewService.getAllReview();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "409", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),

    })
    @PostMapping("/addReview")
    public ResponseEntity<Object> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        return reviewService.deleteReview(id);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return reviewService.updateReview(id, requestBody.get("text").asText());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PostMapping("/addLike")
    public ResponseEntity<Object> addLike(@RequestBody ReviewHistoryWithReview reviewLike) {
        return reviewService.addLike(reviewLike);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @PutMapping("/changeLikeType/{id}")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(@PathVariable("id") Long id) {
        return reviewService.changeLikeTypeOfReview(id);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "id", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<Object> deleteLike(@PathVariable("id") Long id) {
        return reviewService.deleteReviewLike(id);
    }
}
