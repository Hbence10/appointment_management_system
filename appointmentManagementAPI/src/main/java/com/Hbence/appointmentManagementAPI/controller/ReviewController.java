package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import com.Hbence.appointmentManagementAPI.service.ReviewService;
import com.Hbence.appointmentManagementAPI.service.other.ReviewHistoryWithReview;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //Review:
    @Operation(summary = "Review-k visszaszerzése", description = "Az összes review megszerzése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Review.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Review>> getAllReview() {
        return reviewService.getAllReview();
    }

    @Operation(summary = "Review írása", description = "Review írása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új review object-je.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Review.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres review írása", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Review.class, description = "")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó írta a review-t.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Olyan felhasználó írt review-t aki már írt egyszer.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Az object egyik elemével van baj. invalidObject: Az id nem egyenlő null-al, invalidRating: Az adott értékelés vagy kisebb mint 0 vagy nagyobb mint 5.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása request body nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),

    })
    @PostMapping("/addReview")
    public ResponseEntity<Object> addReview(@RequestBody Review newReview) {
        return reviewService.addReview(newReview);
    }

    @Operation(summary = "Review törlése", description = "Review törlése id alapján")
    @Parameter(name = "id", description = "A review-hoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező review törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        return reviewService.deleteReview(id);
    }

     @Operation(summary = "Review frissitése", description = "Review frissitése id alapján.")
    @Parameter(name = "id", description = "A review-hoz tartozó id.", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = @SchemaProperty(name = "text",schema = @Schema(
                    implementation = String.class, description = "Az adott review-nak a frissitett szövege.")
            )
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Review.class, description = "A frissitett review object.")
            )),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return reviewService.updateReview(id, requestBody.get("text").asText(null));
    }

//    VISSZA VAN A DOKUMENTÁLÁS
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

    @Operation(summary = "Like változtatása", description = "Az adott review-hoz tartozó adott like tipusának módositása")
    @Parameter(name = "id", description = "Az adott like-hoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres változtatás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReviewLikeHistory.class, description = "A megváltoztatott like object-je.")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező like változtatása.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/changeLikeType/{id}")
    public ResponseEntity<ReviewLikeHistory> changeLikeTypeOfReview(@PathVariable("id") Long id) {
        return reviewService.changeLikeTypeOfReview(id);
    }

    @Operation(summary = "Like törlése", description = "Like törlése id alapján.")
    @Parameter(name = "id", description = "A like-hoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező like törlése.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<Object> deleteLike(@PathVariable("id") Long id) {
        return reviewService.deleteReviewLike(id);
    }
}
