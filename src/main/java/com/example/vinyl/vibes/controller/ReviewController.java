package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.ReviewsDTO;
import com.example.vinyl.vibes.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewsDTO reviewsDTO) {
        return reviewService.createReview(reviewsDTO);
    }

    @GetMapping("{albumId}")
    public ResponseEntity<?> getForAlbum(@PathVariable String albumId) {
        return reviewService.getAllReviews(albumId);
    }

}
