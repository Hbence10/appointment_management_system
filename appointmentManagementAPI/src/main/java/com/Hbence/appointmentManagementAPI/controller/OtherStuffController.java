package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    //Galleria:
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/gallery")
    public ResponseEntity<List<Gallery>> getAllGalleryImages() {
        return otherStuffService.getGalleryImages();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/gallery/update")
    public ResponseEntity<Gallery> updateGalleryImage(@RequestBody Gallery updatedGallery) {
        return otherStuffService.updateGalleryImage(updatedGallery);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true)
    @ApiResponses({

    })
    @PostMapping("/gallery/addImage")
    public ResponseEntity<Object> addGalleryImage(@RequestParam("galleryImg") MultipartFile galleryImg) {
        return otherStuffService.addGalleryImage(galleryImg);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @ApiResponses({

    })
    @DeleteMapping("/gallery/{id}")
    public ResponseEntity<Object> deleteGalleryImage(@PathVariable("id") Long id) {
        return otherStuffService.deleteGalleryImage(id);
    }

    //Szabalyzat:
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/rule")
    public ResponseEntity<Rules> getRule() {
        return otherStuffService.getRule();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/rule/update")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule) {
        return otherStuffService.updateRules(updatedRule);
    }

    //History
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistory() {
        return otherStuffService.getHistory();
    }

    //Adatok
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/details")
    public ResponseEntity<Details> getDetails() {
        return otherStuffService.getDetails();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/details/update")
    public ResponseEntity<Details> updateDetails(@RequestBody Details updatedDetails) {
        return otherStuffService.updateDetails(updatedDetails);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/openingDetails")
    public ResponseEntity<List<OpeningDetails>> getOpeningDetails() {
        return otherStuffService.getOpeningDetails();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/openingDetails/update")
    public ResponseEntity<List<OpeningDetails>> updateOpeningDetails(@RequestBody List<OpeningDetails> updatedOpeningDetails) {
        return otherStuffService.updateOpeningDetails(updatedOpeningDetails);
    }
}
