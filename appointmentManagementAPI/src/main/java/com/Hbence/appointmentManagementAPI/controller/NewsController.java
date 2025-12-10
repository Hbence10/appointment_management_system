package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.News;
import com.Hbence.appointmentManagementAPI.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
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
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/getAll")
    public ResponseEntity<List<News>> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/addNews")
    public ResponseEntity<News> addNewNews(@RequestBody News newNews) {
        return newsService.addNewNews(newNews);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PatchMapping("/addCoverImg/{id}")
    public ResponseEntity<News> addCoverImg(@PathVariable("id") Long id, @RequestParam("coverImg") MultipartFile coverImg){
        return newsService.addCoverImg(id, coverImg);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PutMapping("/update")
    public ResponseEntity<News> updateNews(@RequestBody News updatedNews) {
        return newsService.updateNews(updatedNews);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id) {
        return newsService.deleteNews(id);
    }
}
