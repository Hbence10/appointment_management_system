package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import com.Hbence.appointmentManagementAPI.service.GalleryService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Galléria képeinek lekérdezése", description = "Galléria képeinek lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Gallery.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<List<Gallery>> getAllGalleryImages() {
        return galleryService.getGalleryImages();
    }

    @Operation(summary = "Galléria kép módositása", description = "Galléria kép módositása")
    @Parameters({
            @Parameter(name = "id", description = "Az adott galléria képhez tartozó id.", in = ParameterIn.PATH, required = true),
            @Parameter(name = "galleryImg", description = "Az új képnek a fájla", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Gallery.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező galléria kép frissités", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameterek nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateGalleryImage(@PathVariable("id") Long id, @RequestParam("galleryImg") MultipartFile galleryImg, @RequestParam("placement") Integer placement, @RequestParam("photoName") String photoName) {
        return galleryService.updateGalleryImage(galleryImg, id, placement, photoName);
    }

    @Operation(summary = "Fénykép hozzáadása a gallériához", description = "Fénykép hozzáadása a gallériához")
    @Parameters({
            @Parameter(name = "galleryImg", description = "A gallériához adott kép", in = ParameterIn.QUERY, required = true, schema = @Schema(implementation = MultipartFile.class)),
            @Parameter(name = "placement", description = "Az új kép helyzete a sorrendben", in = ParameterIn.PATH, required = true, schema = @Schema(implementation = Integer.class))
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres feltöltés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Gallery.class, description = "A feltöltött galléria kép object-je.")
            )),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter(ek) nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @PostMapping("/addImage")
    public ResponseEntity<Object> addGalleryImage(@RequestParam("galleryImg") MultipartFile galleryImg, @RequestParam("placement") Integer placement, @RequestParam("photoName") String photoName) {
        return galleryService.addGalleryImage(galleryImg, placement, photoName);
    }

    @Operation(summary = "Galléria kép törlése", description = "Galléria kép törlése")
    @Parameter(name = "id", description = "Az adott galléria képhez tartozó id.", required = true, in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező kép törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteGalleryImage(@PathVariable("id") Long id) {
        return galleryService.deleteGalleryImage(id);
    }

    @Operation(summary = "Képek új sorrendjének mentése", description = "Képek új sorrendjének mentése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új sorrendű galléria képek listája", required = true, content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Gallery.class))
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Az új sorrendű galléria képek listája", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Gallery.class))
            )),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PutMapping("/updateOrder")
    public ResponseEntity<Object> updateOrder(@RequestBody List<Gallery> updatedOrderList) {
        return galleryService.updateOrder(updatedOrderList);
    }
}
