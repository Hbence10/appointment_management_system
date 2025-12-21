package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {
    private final GalleryService galleryService;

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("")
    public ResponseEntity<List<Gallery>> getAllGalleryImages() {
        return galleryService.getGalleryImages();
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateGalleryImage(@PathVariable("id") Long id, @RequestParam("galleryImg") MultipartFile galleryImg) {
        return galleryService.updateGalleryImage(galleryImg, id);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true)
    @ApiResponses({

    })
    @PostMapping("/addImage")
    public ResponseEntity<Object> addGalleryImage(@RequestParam("galleryImg") MultipartFile galleryImg) {
        return galleryService.addGalleryImage(galleryImg);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @ApiResponses({

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGalleryImage(@PathVariable("id") Long id) {
        return galleryService.deleteGalleryImage(id);
    }

    @PutMapping("/setOrder")
    public ResponseEntity<List<Gallery>> setOrder(@RequestBody List<Gallery> newOrderList) {
        return null;
    }
}
