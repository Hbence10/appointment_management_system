package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
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
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/getAll")
    public ResponseEntity<List<News>> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/addNews")
    public ResponseEntity<Object> addNewNews(@RequestBody News newNews) {
        return newsService.addNewNews(newNews);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @Parameter(name = "", description = "", required = true, in = ParameterIn.QUERY)
    @ApiResponses({

    })
    @PatchMapping("/addCoverImg/{id}")
    public ResponseEntity<Object> addCoverImg(@PathVariable("id") Long id, @RequestParam("coverImg") MultipartFile coverImg) {
        return newsService.addCoverImg(id, coverImg);
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateNews(@RequestBody News updatedNews) {
        return newsService.updateNews(updatedNews);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @ApiResponses({

    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id) {
        return newsService.deleteNews(id);
    }
}
