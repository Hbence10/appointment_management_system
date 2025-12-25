package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
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
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    @Operation(summary = "Hírek lekérdezése", description = "Hírek lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = News.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<News>> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(summary = "Hír létrehozása", description = "Hír létrehozása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új hírnek az object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = News.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres hír létrehozás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = News.class, description = "")
            )),
            @ApiResponse(responseCode = "409", description = "Duplikált hír cím létrehozás", content = @Content),
            @ApiResponse(responseCode = "415", description = "A küldött object id-ja nem egyenlő nullával.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("/addNews")
    public ResponseEntity<Object> addNewNews(@RequestBody News newNews) {
        return newsService.addNewNews(newNews);
    }

    @Operation(summary = "Boritókép hozzáadása", description = "Az adott hírhez boritókép adása/frissitése")
    @Parameters({
            @Parameter(name = "id", description = "A hírhez tartozó id.", required = true, in = ParameterIn.PATH),
            @Parameter(name = "coverImg", description = "A hírhez kívánt boritókép fájlja", required = true, in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres módositás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = News.class, description = "A boritókép elérési útvonalával rendelkező News object.")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező hír módositása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása paraméter és requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PatchMapping("/addCoverImg/{id}")
    public ResponseEntity<Object> addCoverImg(@PathVariable("id") Long id, @RequestParam("coverImg") MultipartFile coverImg) {
        return newsService.addCoverImg(id, coverImg);
    }

    @Operation(summary = "Hír frissitése", description = "Hír frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Frissitett hír object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = News.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = News.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező hír frissitése.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált hír cím regisztrálása.", content = @Content),
            @ApiResponse(responseCode = "415", description = "A küldött object id-ja egyenlő null-lal.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateNews(@RequestBody News updatedNews) {
        return newsService.updateNews(updatedNews);
    }

    @Operation(summary = "Hír törlése", description = "Hír törlése id alapján")
    @Parameter(name = "id", description = "A hírhez tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező hír törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id) {
        return newsService.deleteNews(id);
    }
}
