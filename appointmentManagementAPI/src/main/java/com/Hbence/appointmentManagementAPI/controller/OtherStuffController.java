package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.CloseReasonRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedDateRepository;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    //Galleria:
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/gallery")
    public ResponseEntity<List<Gallery>> getAllGalleryImages() {
        return otherStuffService.getGalleryImages();
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PutMapping("/gallery/update")
    public ResponseEntity<Gallery> updateGalleryImage(@RequestBody Gallery updatedGallery) {
        return otherStuffService.updateGalleryImage(updatedGallery);
    }

    //Szabalyzat:
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/rule")
    public ResponseEntity<Rules> getRule() {
        return otherStuffService.getRule();
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PutMapping("/rule/update")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule) {
        return otherStuffService.updateRules(updatedRule);
    }

    //History
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistory(){
        return otherStuffService.getHistory();
    }

    //Adatok
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/details")
    public ResponseEntity<Details> getDetails(){
        return otherStuffService.getDetails();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/details/update")
    public ResponseEntity<Details> updateDetails(@RequestBody Details updatedDetails){
        return otherStuffService.updateDetails(updatedDetails);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/openingDetails")
    public ResponseEntity<List<OpeningDetails>> getOpeningDetails(){
        return otherStuffService.getOpeningDetails();
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PutMapping("/openingDetails/update")
    public ResponseEntity<OpeningDetails> updateOpeningDetails(@RequestBody OpeningDetails updatedOpeningDetails){
        return null;
    }
}
