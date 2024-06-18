package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.ReviewsDTO;
import com.example.vinyl.vibes.service.impl.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewsDTO reviewsDTO) {
        return reviewServiceImpl.createReview(reviewsDTO);
    }

    @GetMapping("{albumId}")
    public ResponseEntity<?> getForAlbum(@PathVariable String albumId) {
        return reviewServiceImpl.getAllReviews(albumId);
    }

}
