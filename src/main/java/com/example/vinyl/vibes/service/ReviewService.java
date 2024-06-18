package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.ReviewsDTO;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<?> createReview(ReviewsDTO reviewsDTO);

    ResponseEntity<?> getAllReviews(String albumId);
}
